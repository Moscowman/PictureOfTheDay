package ru.varasoft.pictureoftheday.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.varasoft.pictureoftheday.BuildConfig
import ru.varasoft.pictureoftheday.model.*
import ru.varasoft.pictureoftheday.model.mars.MarsManifestServerResponseData
import ru.varasoft.pictureoftheday.model.mars.MarsPhotoArrayServerResponseData
import ru.varasoft.pictureoftheday.model.mars.MarsPhotoData
import ru.varasoft.pictureoftheday.util.Util
import ru.varasoft.pictureoftheday.view.MarsView
import javax.inject.Inject

class MarsPhotoPresenter(
    private val router: Router,
    private val retrofitImpl: RetrofitImpl = RetrofitImpl(),
    var roversManifest: Response<MarsManifestServerResponseData>? = null
) :
    MvpPresenter<MarsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getData(Util.getDateRelativeToToday(0))
    }

    fun getData(date: String) {
        getMarsManifest("perseverance", date)
    }

    private fun getMarsManifest(roverName: String, date: String) {
        if (roversManifest != null) {
            sendServerRequest(roverName, date)
        }
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            MarsPhotoData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getMarsManifestRetrofitImpl().getMarsManifest(roverName, apiKey)
                .enqueue(object :
                    Callback<MarsManifestServerResponseData> {
                    override fun onResponse(
                        call: Call<MarsManifestServerResponseData>,
                        response: Response<MarsManifestServerResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            val maxDate = response.body()!!.photoManifest?.maxDate
                            if (maxDate != null) {
                                sendServerRequest(roverName, maxDate)
                            }
                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()) {
                            } else {
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<MarsManifestServerResponseData>,
                        t: Throwable
                    ) {
                    }
                })
        }
    }

    private fun sendServerRequest(roverName: String, date: String) {
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            MarsPhotoData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getMarsPhotoRetrofitImpl()
                .getMarsPhoto(roverName, apiKey, date).enqueue(object :
                    Callback<MarsPhotoArrayServerResponseData> {
                    override fun onResponse(
                        call: Call<MarsPhotoArrayServerResponseData>,
                        response: Response<MarsPhotoArrayServerResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            viewState.displayPicture(response.body()!!.photos[0].imgSrc)
                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()) {
                            } else {
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<MarsPhotoArrayServerResponseData>,
                        t: Throwable
                    ) {
                    }
                })
        }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}

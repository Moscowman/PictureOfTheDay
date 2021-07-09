package ru.varasoft.pictureoftheday.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.varasoft.pictureoftheday.BuildConfig
import ru.varasoft.pictureoftheday.model.RetrofitImpl
import ru.varasoft.pictureoftheday.model.pod.PODServerResponseData
import ru.varasoft.pictureoftheday.model.pod.PictureOfTheDayData
import ru.varasoft.pictureoftheday.view.PODView
import javax.inject.Inject

class EarthPhotoPresenter(
    private val retrofitImpl: RetrofitImpl
) :
    MvpPresenter<PODView>()  {

    @Inject
    lateinit var router: Router

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    fun displayPicture(date: String) {
        sendServerRequest(date)
    }

    private fun sendServerRequest(date: String) {
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getPODRetrofitImpl().getPictureOfTheDay(apiKey, "true", date).enqueue(object :
                Callback<PODServerResponseData> {
                override fun onResponse(
                    call: Call<PODServerResponseData>,
                    response: Response<PODServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        viewState.displayPicture(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                        } else {
                        }
                    }
                }

                override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                }
            })
        }
    }
}

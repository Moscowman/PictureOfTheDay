package ru.varasoft.pictureoftheday.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.varasoft.pictureoftheday.BuildConfig
import ru.varasoft.pictureoftheday.model.*
import ru.varasoft.pictureoftheday.model.mars.MarsManifestServerResponseData
import ru.varasoft.pictureoftheday.model.mars.MarsPhotoArrayServerResponseData
import ru.varasoft.pictureoftheday.model.mars.MarsPhotoData

class MarsPhotoPresenter(
    private val liveDataForViewToObserve: MutableLiveData<MarsPhotoData> = MutableLiveData(),
    private val retrofitImpl: RetrofitImpl = RetrofitImpl(),
    var roversManifest: Response<MarsManifestServerResponseData>? = null
) :
    ViewModel() {

    fun getData(date: String): LiveData<MarsPhotoData> {
        getMarsManifest("perseverance", date)
        return liveDataForViewToObserve
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
                                liveDataForViewToObserve.value =
                                    MarsPhotoData.Error(Throwable("Unidentified error"))
                            } else {
                                liveDataForViewToObserve.value =
                                    MarsPhotoData.Error(Throwable(message))
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<MarsManifestServerResponseData>,
                        t: Throwable
                    ) {
                        liveDataForViewToObserve.value = MarsPhotoData.Error(t)
                    }
                })
        }
    }

    private fun sendServerRequest(roverName: String, date: String) {
        liveDataForViewToObserve.value = MarsPhotoData.Loading(null)
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
                            liveDataForViewToObserve.value =
                                MarsPhotoData.Success(response.body()!!)
                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()) {
                                liveDataForViewToObserve.value =
                                    MarsPhotoData.Error(Throwable("Unidentified error"))
                            } else {
                                liveDataForViewToObserve.value =
                                    MarsPhotoData.Error(Throwable(message))
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<MarsPhotoArrayServerResponseData>,
                        t: Throwable
                    ) {
                        liveDataForViewToObserve.value = MarsPhotoData.Error(t)
                    }
                })
        }
    }
}

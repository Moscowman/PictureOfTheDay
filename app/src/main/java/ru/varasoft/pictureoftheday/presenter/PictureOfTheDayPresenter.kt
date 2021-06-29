package ru.varasoft.pictureoftheday.presenter

import androidx.lifecycle.MutableLiveData
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.varasoft.pictureoftheday.BuildConfig
import ru.varasoft.pictureoftheday.model.RetrofitImpl
import ru.varasoft.pictureoftheday.model.pod.PODServerResponseData
import ru.varasoft.pictureoftheday.model.pod.PictureOfTheDayData
import ru.varasoft.pictureoftheday.view.PODView
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayPresenter(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
    private val retrofitImpl: RetrofitImpl = RetrofitImpl()
) :
    MvpPresenter<PODView>() {
    override fun onFirstViewAttach() {
        displayPicture(getDateRelativeToToday(0))
    }

    var offset: Int = 0

    private fun getDateRelativeToToday(offset: Int): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, offset);
        return sdf.format(calendar.getTime())
    }

    fun renderPuctureRelativeToToday(_offset: Int) {
        offset = _offset
        displayPicture(getDateRelativeToToday(offset))
    }

    fun displayPicture(date: String) {
        sendServerRequest(date)
    }

    private fun sendServerRequest(date: String) {
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getPODRetrofitImpl().getPictureOfTheDay(apiKey, "true", date)
                .enqueue(object :
                    Callback<PODServerResponseData> {
                    override fun onResponse(
                        call: Call<PODServerResponseData>,
                        response: Response<PODServerResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            viewState.displayPicture(response.body()!!)
                        }
                    }

                    override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                        //liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
                    }
                })
        }
    }
}

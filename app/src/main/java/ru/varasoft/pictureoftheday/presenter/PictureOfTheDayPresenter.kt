package ru.varasoft.pictureoftheday.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.varasoft.pictureoftheday.model.RetrofitImpl
import ru.varasoft.pictureoftheday.model.pod.PODServerResponseData
import ru.varasoft.pictureoftheday.model.pod.PictureOfTheDayData
import ru.varasoft.pictureoftheday.util.Util
import ru.varasoft.pictureoftheday.view.PODView
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayPresenter(
    private val router: Router,
    private val retrofitImpl: RetrofitImpl
) :
    MvpPresenter<PODView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        displayPicture(Util.getDateRelativeToToday(0))
    }

    var offset: Int = 0

    fun renderPuctureRelativeToToday(_offset: Int) {
        offset = _offset
        displayPicture(Util.getDateRelativeToToday(offset))
    }

    fun displayPicture(date: String) {
        sendServerRequest(date)
    }

    private fun sendServerRequest(date: String) {
        val apiKey: String = "DEMO_KEY"
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
                    }
                })
        }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}

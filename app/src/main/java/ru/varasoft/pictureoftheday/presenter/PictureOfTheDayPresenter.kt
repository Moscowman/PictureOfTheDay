package ru.varasoft.pictureoftheday.presenter

import android.content.Context
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.varasoft.pictureoftheday.AndroidNetworkStatus
import ru.varasoft.pictureoftheday.INetworkStatus
import ru.varasoft.pictureoftheday.model.RetrofitImpl
import ru.varasoft.pictureoftheday.model.pod.IPODCache
import ru.varasoft.pictureoftheday.model.pod.PODServerResponseData
import ru.varasoft.pictureoftheday.model.pod.PictureOfTheDayData
import ru.varasoft.pictureoftheday.util.Util
import ru.varasoft.pictureoftheday.view.PODView
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayPresenter(
    private val router: Router,
    private val retrofitImpl: RetrofitImpl,
    private val context: Context,
    private val podCache: IPODCache
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
        val apiKey = "DEMO_KEY"
        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
            val ans: INetworkStatus = AndroidNetworkStatus(context)
            ans.isOnlineSingle()
                .flatMap { isOnline ->
                    if (isOnline) {
                        val retrofit = retrofitImpl.getPODRetrofitImpl()
                        retrofit.getPictureOfTheDay(apiKey, "true", date)
                            .flatMap { pod ->
                                Single.fromCallable {
                                    podCache.insertPOD(pod, date)
                                    pod
                                }
                            }
                    } else {
                        Single.fromCallable {
                        }
                    }
                }
                .subscribe({ pod ->
                    viewState.displayPicture(pod as PODServerResponseData)
                }, {
                    println("Error: ${it.message}")
                })

        }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}

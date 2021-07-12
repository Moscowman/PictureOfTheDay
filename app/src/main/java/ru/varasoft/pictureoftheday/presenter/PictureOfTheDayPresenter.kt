package ru.varasoft.pictureoftheday.presenter

import android.content.Context
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.varasoft.pictureoftheday.AndroidNetworkStatus
import ru.varasoft.pictureoftheday.INetworkStatus
import ru.varasoft.pictureoftheday.model.RetrofitImpl
import ru.varasoft.pictureoftheday.model.pod.*
import ru.varasoft.pictureoftheday.util.Util
import ru.varasoft.pictureoftheday.view.PODView
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class PictureOfTheDayPresenter(
    private val router: Router,
    private val nasaPODRepo: INasaPODRepo
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

    private fun displayPicture(date: String) {
        val apiKey = "DEMO_KEY"
        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
            nasaPODRepo.getPicture(apiKey, date)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ pod ->
                    viewState.displayPicture(pod)
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

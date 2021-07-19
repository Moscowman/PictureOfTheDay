package ru.varasoft.pictureoftheday.presenter

import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import moxy.MvpPresenter
import ru.gb.gb_popular_libs.data.network.NetworkStateRepository
import ru.varasoft.pictureoftheday.RxBus
import ru.varasoft.pictureoftheday.model.NASAApiInterceptor.apiKey
import ru.varasoft.pictureoftheday.model.NetworkState
import ru.varasoft.pictureoftheday.model.pod.INasaPODRepo
import ru.varasoft.pictureoftheday.model.pod.PictureOfTheDayData
import ru.varasoft.pictureoftheday.util.Util
import ru.varasoft.pictureoftheday.view.PODView

class PictureOfTheDayPresenter(
    private val networkStateRepository: NetworkStateRepository,
    private val router: Router,
    private val nasaPODRepo: INasaPODRepo,
) :
    MvpPresenter<PODView>() {

    private var disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
      disposables +=
            networkStateRepository
                .watchForNetworkState()
                .filter { networkState -> networkState == NetworkState.CONNECTED }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { displayPicture(Util.getDateRelativeToToday(0)) }
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.newThread())
                .subscribe()

        super.onFirstViewAttach()
        displayPicture(Util.getDateRelativeToToday(0))
    }

    override fun onDestroy() {
        super.onDestroy()

        disposables.dispose()
    }

    var offset: Int = 0

    fun renderPuctureRelativeToToday(_offset: Int) {
        offset = _offset
        displayPicture(Util.getDateRelativeToToday(offset))
    }

    private fun displayPicture(date: String) {
        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
            nasaPODRepo.getPicture(date)
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

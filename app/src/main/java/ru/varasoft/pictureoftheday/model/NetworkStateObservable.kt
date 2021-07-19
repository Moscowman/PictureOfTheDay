package ru.gb.gb_popular_libs.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import io.reactivex.rxjava3.android.MainThreadDisposable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import ru.varasoft.pictureoftheday.model.NetworkState

class NetworkStateObservable(private val context: Context): Observable<NetworkState>() {

    override fun subscribeActual(observer: Observer<in NetworkState>) {
        val listener = NetworkStateListener(context, observer)
        observer.onSubscribe(listener)
    }

    class NetworkStateListener(
        private val context: Context,
        private val observer: Observer<in NetworkState>
    ) : ConnectivityManager.NetworkCallback(), Disposable {

        private val disposable = object : MainThreadDisposable() {
            override fun onDispose() {
                connectivityManager.unregisterNetworkCallback(this@NetworkStateListener)
            }
        }

        private val connectivityManager: ConnectivityManager by lazy {
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }

        init {
            connectivityManager
                .registerNetworkCallback(NetworkRequest.Builder().build(), this)
        }

        override fun onAvailable(network: Network) {
            observer.onNext(NetworkState.CONNECTED)
        }

        override fun onUnavailable() {
            observer.onNext(NetworkState.DISCONNECTED)
        }

        override fun onLost(network: Network) {
            observer.onNext(NetworkState.DISCONNECTED)
        }

        override fun dispose() = disposable.dispose()

        override fun isDisposed(): Boolean = disposable.isDisposed

    }

}
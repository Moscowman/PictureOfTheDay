package ru.gb.gb_popular_libs.data.network

import android.content.Context
import io.reactivex.rxjava3.core.Observable
import ru.varasoft.pictureoftheday.model.NetworkState

class NetworkStateRepositoryImpl(private val context: Context) : NetworkStateRepository {

    override fun watchForNetworkState(): Observable<NetworkState> =
        NetworkStateObservable(context)
            .cacheWithInitialCapacity(1)
            .skip(1)

}
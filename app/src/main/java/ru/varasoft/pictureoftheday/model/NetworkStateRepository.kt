package ru.gb.gb_popular_libs.data.network

import io.reactivex.rxjava3.core.Observable
import ru.varasoft.pictureoftheday.model.NetworkState


interface NetworkStateRepository {

    fun watchForNetworkState(): Observable<NetworkState>

}
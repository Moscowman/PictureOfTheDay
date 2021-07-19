package ru.varasoft.pictureoftheday

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.gb.gb_popular_libs.data.network.NetworkStateRepository
import ru.gb.gb_popular_libs.data.network.NetworkStateRepositoryImpl

@Module
class NetworkStateModule {

    @Provides
    fun provideNetworkStateRepository(context: Context): NetworkStateRepository =
        NetworkStateRepositoryImpl(context)

}
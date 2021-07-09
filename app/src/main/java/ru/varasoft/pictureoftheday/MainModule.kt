package ru.varasoft.pictureoftheday

import dagger.Module
import dagger.Provides
import ru.varasoft.pictureoftheday.model.RetrofitImpl
import javax.inject.Singleton

@Module
class MainModule {

    @Singleton
    @Provides
    fun retrofitImpl(): RetrofitImpl = RetrofitImpl()
}

package ru.varasoft.pictureoftheday

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {
    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: PictureOfTheDayComponent

    override fun applicationInjector(): AndroidInjector<App> =
        DaggerPictureOfTheDayComponent
            .builder()
            .withContext(applicationContext)
            .withRouter(cicerone.router)
            .application(this)
            .build()

    //Временно до даггера положим это тут
    private val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
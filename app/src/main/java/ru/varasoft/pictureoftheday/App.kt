package ru.varasoft.pictureoftheday

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<App> =
        DaggerPictureOfTheDayComponent
            .builder()
            .withContext(applicationContext)
            .apply {
                val cicerone = Cicerone.create()

                withRouter(cicerone.router)
                withNavigatorHolder(cicerone.getNavigatorHolder())
            }
            .build()
}
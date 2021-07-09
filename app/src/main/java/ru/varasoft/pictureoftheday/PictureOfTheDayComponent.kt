package ru.varasoft.pictureoftheday

import android.content.Context
import com.github.terrakok.cicerone.Router
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import ru.varasoft.pictureoftheday.presenter.PictureOfTheDayPresenter
import ru.varasoft.popularlibs.MainPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, UiModule::class, MainModule::class])
interface PictureOfTheDayComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun withContext(context: Context): Builder

        @BindsInstance
        fun withRouter(router: Router): Builder

        fun build(): PictureOfTheDayComponent
    }
}
package ru.varasoft.pictureoftheday

import android.content.Context
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import ru.varasoft.pictureoftheday.presenter.PictureOfTheDayPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, UiModule::class, MainModule::class, NetworkStateModule::class])
interface PictureOfTheDayComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun withContext(context: Context): Builder

        @BindsInstance
        fun withRouter(router: Router): Builder

        @BindsInstance
        fun withNavigatorHolder(navigatorHolder: NavigatorHolder): Builder

        fun build(): PictureOfTheDayComponent
    }

    fun inject(pictureOfTheDayPresenter: PictureOfTheDayPresenter)
}
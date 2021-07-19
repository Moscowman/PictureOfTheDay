package ru.varasoft.pictureoftheday

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.varasoft.pictureoftheday.model.earth.EarthFragment
import ru.varasoft.pictureoftheday.view.MarsFragment
import ru.varasoft.pictureoftheday.view.PODFragment

@Module
abstract class UiModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindPODFragment(): PODFragment

    @ContributesAndroidInjector
    abstract fun bindEarthFragment(): EarthFragment

    @ContributesAndroidInjector
    abstract fun bindMarsFragment(): MarsFragment
}

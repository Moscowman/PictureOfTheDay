package ru.varasoft.pictureoftheday

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.varasoft.pictureoftheday.view.PODFragment

@Module
abstract class UiModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindPODFragment(): PODFragment

}

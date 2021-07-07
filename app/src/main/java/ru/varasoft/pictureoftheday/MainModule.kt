package ru.varasoft.pictureoftheday

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.varasoft.pictureoftheday.view.PODFragment

@Module
abstract class MainModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindPODFragment(): PODFragment

}

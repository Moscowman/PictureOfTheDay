package ru.varasoft.pictureoftheday

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.varasoft.pictureoftheday.view.PODFragment

@Module
abstract class PODModule {
    @ContributesAndroidInjector
    abstract fun bindPODFragment(): Fragment
}
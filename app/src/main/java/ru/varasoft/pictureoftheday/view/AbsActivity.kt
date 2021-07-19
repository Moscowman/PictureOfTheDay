package ru.varasoft.pictureoftheday.view

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.github.terrakok.cicerone.Router
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import moxy.MvpAppCompatActivity
import javax.inject.Inject

abstract class AbsActivity(@LayoutRes contentLayoutId: Int) : MvpAppCompatActivity(contentLayoutId), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

}
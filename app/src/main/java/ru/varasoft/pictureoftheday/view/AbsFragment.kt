package ru.varasoft.pictureoftheday.view

import android.content.Context
import androidx.annotation.LayoutRes
import com.github.terrakok.cicerone.Router
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import moxy.MvpAppCompatFragment
import javax.inject.Inject

abstract class AbsFragment(@LayoutRes contentLayoutId: Int) : MvpAppCompatFragment(contentLayoutId), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var router: Router

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

}
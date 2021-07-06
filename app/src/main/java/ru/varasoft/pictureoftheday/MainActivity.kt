package ru.varasoft.pictureoftheday

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.varasoft.pictureoftheday.databinding.ActivityMainBinding
import ru.varasoft.pictureoftheday.view.PODFragment
import ru.varasoft.pictureoftheday.view.SETTINGS_SHARED_PREFERENCE
import ru.varasoft.pictureoftheday.view.SettingsFragment
import ru.varasoft.pictureoftheday.view.THEME_RES_ID
import ru.varasoft.popularlibs.MainPresenter
import ru.varasoft.popularlibs.MainView
import javax.inject.Inject


class MainActivity : MvpAppCompatActivity(), MainView, HasAndroidInjector {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    val navigator = AppNavigator(this, R.id.container)

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private val presenter by moxyPresenter { MainPresenter(router) }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        val resIdTheme = getSharedPreferences(SETTINGS_SHARED_PREFERENCE, Context.MODE_PRIVATE)
            .getInt(THEME_RES_ID, R.style.Theme_PictureOfTheDay)
        setTheme(resIdTheme)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomNavigationView()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.instance.navigatorHolder.removeNavigator()
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_telescope -> {
                    replaceFragment(PODFragment())
                    true
                }
                R.id.bottom_view_settings -> {
                    replaceFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNowAllowingStateLoss()
    }

}

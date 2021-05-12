package ru.varasoft.pictureoftheday

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import geekbarains.material.ui.api.MarsFragment
import geekbarains.material.ui.api.WeatherFragment
import ru.varasoft.pictureoftheday.databinding.ActivityMainBinding
import ru.varasoft.pictureoftheday.model.earth.EarthFragment
import ru.varasoft.pictureoftheday.view.PODFragment
import ru.varasoft.pictureoftheday.view.SETTINGS_SHARED_PREFERENCE
import ru.varasoft.pictureoftheday.view.SettingsFragment
import ru.varasoft.pictureoftheday.view.THEME_RES_ID


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        val resIdTheme = getSharedPreferences(SETTINGS_SHARED_PREFERENCE, Context.MODE_PRIVATE)
            .getInt(THEME_RES_ID, R.style.Theme_PictureOfTheDay)
        setTheme(resIdTheme)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            replaceFragment(PODFragment.newInstance())
        }

        setBottomNavigationView()
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_telescope -> {
                    replaceFragment(PODFragment())
                    true
                }
                R.id.bottom_view_earth -> {
                    replaceFragment(EarthFragment())
                    true
                }
                R.id.bottom_view_mars -> {
                    replaceFragment(MarsFragment())
                    true
                }
                R.id.bottom_view_weather -> {
                    replaceFragment(WeatherFragment())
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

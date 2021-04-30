package ru.varasoft.pictureoftheday

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import geekbarains.material.ui.api.EarthFragment
import geekbarains.material.ui.api.MarsFragment
import geekbarains.material.ui.api.WeatherFragment
import ru.varasoft.pictureoftheday.databinding.ActivityMainBinding
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
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PODFragment.newInstance())
                .commitNow()
        }
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_telescope -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, PODFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bottom_view_earth -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, EarthFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bottom_view_mars -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MarsFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bottom_view_weather -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, WeatherFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bottom_view_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SettingsFragment())
                        .commitAllowingStateLoss()
                    true
                }
                else -> false
            }
        }
    }
}

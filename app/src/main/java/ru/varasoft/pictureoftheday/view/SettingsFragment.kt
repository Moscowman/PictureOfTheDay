package ru.varasoft.pictureoftheday.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.varasoft.pictureoftheday.R
import ru.varasoft.pictureoftheday.databinding.FragmentSettingsBinding

const val SETTINGS_SHARED_PREFERENCE = "SETTINGS_SHARED_PREFERENCE"
const val THEME_NAME_SHARED_PREFERENCE = "THEME_NAME_SHARED_PREFERENCE"
const val THEME_RES_ID = "THEME_RES_ID"

const val EARTH = "EARTH"
const val MARS = "MARS"
const val MOON = "MOON"

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var savedThemeName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadSharedPreferenceSettings()

        binding.marsianThemeChip.setOnClickListener {
            if (savedThemeName != MARS) {
                saveThemeSettings(MARS, R.style.MarsianTheme)
                activity?.recreate()
            }
        }
        binding.earthThemeChip.setOnClickListener {
            if (savedThemeName != EARTH) {
                saveThemeSettings(EARTH, R.style.Theme_PictureOfTheDay)
                activity?.recreate()
            }
        }
        binding.moonThemeChip.setOnClickListener {
            if (savedThemeName != MOON) {
                saveThemeSettings(MOON, R.style.MoonTheme)
                activity?.recreate()
            }
        }
    }

    private fun saveThemeSettings(themeName: String, id: Int) {
        this.savedThemeName = themeName
        activity?.let {
            with(it.getSharedPreferences(SETTINGS_SHARED_PREFERENCE, Context.MODE_PRIVATE).edit()) {
                putString(THEME_NAME_SHARED_PREFERENCE, themeName).apply()
                putInt(THEME_RES_ID, id).apply()
            }
        }
    }

    private fun loadSharedPreferenceSettings() {
        requireActivity().let {
            savedThemeName =
                it.getSharedPreferences(SETTINGS_SHARED_PREFERENCE, Context.MODE_PRIVATE)
                    .getString(THEME_NAME_SHARED_PREFERENCE, EARTH).toString()
            when (savedThemeName) {
                MOON -> {
                    binding.moonThemeChip.isChecked = true
                }
                MARS -> {
                    binding.marsianThemeChip.isChecked = true
                }
                EARTH -> {
                    binding.earthThemeChip.isChecked = true
                }
                else -> {
                    binding.earthThemeChip.isChecked = true
                }
            }
        }
    }
}

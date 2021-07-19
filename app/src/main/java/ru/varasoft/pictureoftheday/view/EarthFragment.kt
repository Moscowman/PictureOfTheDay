package ru.varasoft.pictureoftheday.model.earth

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.load
import com.github.terrakok.cicerone.Router
import kotlinx.android.synthetic.main.fragment_earth.*
import kotlinx.android.synthetic.main.fragment_mars.*
import moxy.ktx.moxyPresenter
import ru.varasoft.pictureoftheday.R
import ru.varasoft.pictureoftheday.model.EarthModel
import ru.varasoft.pictureoftheday.presenter.EarthPhotoPresenter
import ru.varasoft.pictureoftheday.view.AbsFragment
import ru.varasoft.pictureoftheday.view.BackButtonListener
import ru.varasoft.pictureoftheday.view.EarthView
import java.util.*
import javax.inject.Inject

class EarthFragment : AbsFragment(R.layout.fragment_earth), EarthView, BackButtonListener {

    @Inject
    lateinit var earthModel: EarthModel

    private val presenter: EarthPhotoPresenter by moxyPresenter {
        EarthPhotoPresenter(earthModel, requireContext())
    }

    override fun backPressed() = presenter.backPressed()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestLocationPermission()
    }

    fun requestLocationPermission() {
        try {
            if (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    101
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun displayPicture(url: String) {
        earth_image_view.load(url) {
            lifecycle(this@EarthFragment)
            error(R.drawable.ic_load_error_vector)
            placeholder(R.drawable.ic_no_photo_vector)
        }
    }
}

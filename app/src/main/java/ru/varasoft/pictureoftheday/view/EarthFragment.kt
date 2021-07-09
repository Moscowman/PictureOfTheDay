package ru.varasoft.pictureoftheday.model.earth

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
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
import ru.varasoft.pictureoftheday.model.RetrofitImpl
import ru.varasoft.pictureoftheday.presenter.EarthPhotoPresenter
import ru.varasoft.pictureoftheday.view.AbsFragment
import ru.varasoft.pictureoftheday.view.BackButtonListener
import ru.varasoft.pictureoftheday.view.EarthView
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class EarthFragment : AbsFragment(R.layout.fragment_earth), EarthView, BackButtonListener {

    var location: Location? = null

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var retrofitImpl: RetrofitImpl

    private val presenter: EarthPhotoPresenter by moxyPresenter {
        EarthPhotoPresenter(retrofitImpl)
    }

    override fun backPressed() = presenter.backPressed()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earth, container, false)
    }

    fun getCurrentLocation() {
        val locationManager: LocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager;

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission()
            return
        }
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getCurrentLocation()
        location?.let { renderData(it) }
    }

    private fun renderData(location: Location) {
        val lat = location.latitude
        val lon = location.longitude
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentTime = Calendar.getInstance().time
        val date = sdf.format(currentTime)
        val url =
            "https://api.nasa.gov/planetary/earth/imagery?lon=$lon&lat=$lat&dim=0.15&date=$date&api_key=DEMO_KEY"
        earth_image_view.load(url) {
            lifecycle(this@EarthFragment)
            error(R.drawable.ic_load_error_vector)
            placeholder(R.drawable.ic_no_photo_vector)
        }
    }
}

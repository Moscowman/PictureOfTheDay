package geekbarains.material.ui.api

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
import androidx.fragment.app.Fragment
import coil.load
import kotlinx.android.synthetic.main.fragment_earth.*
import kotlinx.android.synthetic.main.fragment_mars.*
import ru.varasoft.pictureoftheday.R
import java.text.SimpleDateFormat
import java.util.*

class EarthFragment : Fragment() {

    var location: Location? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earth, container, false)
    }

    fun getCurrentLocation() {
        val locationManager: LocationManager =
            context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager;

        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context!!,
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
                    getActivity()!!,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    getActivity()!!,
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

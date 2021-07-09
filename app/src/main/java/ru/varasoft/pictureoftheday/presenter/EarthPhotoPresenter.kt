package ru.varasoft.pictureoftheday.presenter

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.varasoft.pictureoftheday.BuildConfig
import ru.varasoft.pictureoftheday.model.RetrofitImpl
import ru.varasoft.pictureoftheday.model.pod.PODServerResponseData
import ru.varasoft.pictureoftheday.model.pod.PictureOfTheDayData
import ru.varasoft.pictureoftheday.util.Util
import ru.varasoft.pictureoftheday.view.EarthView
import ru.varasoft.pictureoftheday.view.PODView
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class EarthPhotoPresenter(
    private val retrofitImpl: RetrofitImpl,
    private val context: Context
) :
    MvpPresenter<EarthView>() {

    @Inject
    lateinit var router: Router

    var location: Location? = null

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getCurrentLocation()
        displayPicture()
    }

    fun getCurrentLocation() {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager;

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
    }

    fun displayPicture() {
        location?.let {
            val lat = it.latitude
            val lon = it.longitude
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val currentTime = Calendar.getInstance().time
            val date = sdf.format(currentTime)
            val url =
                "https://api.nasa.gov/planetary/earth/imagery?lon=$lon&lat=$lat&dim=0.15&date=$date&api_key=DEMO_KEY"
            viewState.displayPicture(url)
        }
    }
}

package ru.varasoft.pictureoftheday.model

import android.location.Location
import java.text.SimpleDateFormat
import java.util.*

class EarthModelImpl : EarthModel {
    override fun getPictureUrlForLocation(location: Location) : String? {
        val lat = location.latitude
        val lon = location.longitude
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentTime = Calendar.getInstance().time
        val date = sdf.format(currentTime)
        val url =
            "https://api.nasa.gov/planetary/earth/imagery?lon=$lon&lat=$lat&dim=0.15&date=$date&api_key=DEMO_KEY"
        return url
    }
}
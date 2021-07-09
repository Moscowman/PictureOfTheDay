package ru.varasoft.pictureoftheday.model

import android.location.Location

interface EarthModel {
    fun getPictureUrlForLocation(location: Location) : String?
}
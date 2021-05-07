package ru.varasoft.pictureoftheday.model.earth

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EarthPhotoAPI {
    @GET("planetary/apod")
    fun getEarthPhoto(@Query("api_key") apiKey: String, @Query("thumbs") thumbs: String, @Query("date") date: String): Call<EarthPhotoServerResponseData>
}
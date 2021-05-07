package ru.varasoft.pictureoftheday.model.earth

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EarthPhotoAPI {
    @GET("planetary/earth/imagery")
    fun getEarthPhoto(@Query("api_key") apiKey: String, @Query("lon") longitude: String, @Query("lat") latitude: String, @Query("date") date: String): Call<EarthPhotoServerResponseData>
}
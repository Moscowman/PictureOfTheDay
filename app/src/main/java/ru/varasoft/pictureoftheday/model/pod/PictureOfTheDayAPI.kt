package ru.varasoft.pictureoftheday.model.pod

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String, @Query("thumbs") thumbs: String, @Query("date") date: String): Call<PODServerResponseData>
}
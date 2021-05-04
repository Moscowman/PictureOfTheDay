package ru.varasoft.pictureoftheday.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarsPhotoAPI {
    @GET("planetary/apod")
    fun getMarsPhoto(@Query("api_key") apiKey: String, @Query("thumbs") thumbs: String, @Query("date") date: String): Call<MarsPhotoServerResponseData>
}
package ru.varasoft.pictureoftheday.model.pod

import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("thumbs") thumbs: String, @Query("date") date: String): Single<PODServerResponseData>
}
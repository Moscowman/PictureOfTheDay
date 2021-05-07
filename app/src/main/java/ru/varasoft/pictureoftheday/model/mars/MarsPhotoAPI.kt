package ru.varasoft.pictureoftheday.model.mars

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarsPhotoAPI {
    @GET("mars-photos/api/v1/rovers/{rover_name}/photos")
    fun getMarsPhoto(@Path("rover_name") roverName: String, @Query("api_key") apiKey: String, @Query("earth_date") date: String): Call<MarsPhotoArrayServerResponseData>
}
package ru.varasoft.pictureoftheday.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarsManifestAPI {
    @GET("mars-photos/api/v1/manifests/{rover_name}")
    fun getMarsManifest(@Path ("rover_name") roverName: String, @Query("api_key") apiKey: String): Call<MarsManifestServerResponseData>
}
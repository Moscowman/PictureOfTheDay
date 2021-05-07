package ru.varasoft.pictureoftheday.model.mars

import com.google.gson.annotations.SerializedName

data class MarsPhotoArrayServerResponseData(
    @field:SerializedName("photos") val photos: Array<MarsPhotoServerResponseData>
)

data class MarsPhotoServerResponseData(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("sol") val sol: Int,
    @field:SerializedName("camera") val camera: MarsCameraServerResponseData,
    @field:SerializedName("img_src") val imgSrc: String,
    @field:SerializedName("earth_date") val earthDate: String,
    @field:SerializedName("rover") val rover: MarsRoverServerResponseData
)

data class MarsCameraServerResponseData(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("rover_id") val roverId: Int,
    @field:SerializedName("full_name") val fullName: String
)

data class MarsRoverServerResponseData(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("landing_date") val landingDate: String,
    @field:SerializedName("launch_date") val launchDate: String,
    @field:SerializedName("status") val status: String
)

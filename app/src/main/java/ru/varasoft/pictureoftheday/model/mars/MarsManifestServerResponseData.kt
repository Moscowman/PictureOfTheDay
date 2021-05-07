package ru.varasoft.pictureoftheday.model.mars

import com.google.gson.annotations.SerializedName

data class MarsManifestServerResponseData(
    @field:SerializedName("photo_manifest") val photoManifest: MarsManifestRoverServerResponseData?
)

data class MarsManifestRoverServerResponseData(
    @field:SerializedName("name") val name: String?,
    @field:SerializedName("launch_date") val launchDate: String?,
    @field:SerializedName("landing_date") val landingDate: String?,
    @field:SerializedName("max_date") val maxDate: String?,
    @field:SerializedName("max_sol") val maxSol: Int?,
    @field:SerializedName("total_photos") val mediaType: Int?
)

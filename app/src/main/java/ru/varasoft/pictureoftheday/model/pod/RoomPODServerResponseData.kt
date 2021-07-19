package ru.varasoft.pictureoftheday.model.pod

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class RoomPODServerResponseData(
    @PrimaryKey
    @SerializedName("id")
    var id: Int,
    @SerializedName("copyright")
    var copyright: String?,
    @SerializedName("date")
    var date: String?,
    @SerializedName("explanation")
    var explanation: String?,
    @SerializedName("media_type")
    var mediaType: String?,
    @SerializedName("thumbnail_url")
    var thumbnailUrl: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("url")
    var url: String?,
    @SerializedName("hdurl")
    var hdurl: String?
)
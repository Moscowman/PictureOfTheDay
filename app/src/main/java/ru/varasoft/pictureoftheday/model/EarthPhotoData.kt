package ru.varasoft.pictureoftheday.model

sealed class EarthPhotoData {
    data class Success(val serverResponseData: EarthPhotoServerResponseData) : EarthPhotoData()
    data class Error(val error: Throwable) : EarthPhotoData()
    data class Loading(val progress: Int?) : EarthPhotoData()
}

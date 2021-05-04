package ru.varasoft.pictureoftheday.model

sealed class MarsPhotoData {
    data class Success(val serverResponseData: MarsPhotoServerResponseData) : MarsPhotoData()
    data class Error(val error: Throwable) : MarsPhotoData()
    data class Loading(val progress: Int?) : MarsPhotoData()
}

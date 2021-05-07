package ru.varasoft.pictureoftheday.model.mars

sealed class MarsPhotoData {
    data class Success(val serverResponseData: MarsPhotoArrayServerResponseData) : MarsPhotoData()
    data class Error(val error: Throwable) : MarsPhotoData()
    data class Loading(val progress: Int?) : MarsPhotoData()
}

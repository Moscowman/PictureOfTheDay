package ru.varasoft.pictureoftheday.model

sealed class MarsManifestData {
    data class Success(val serverResponseData: MarsManifestServerResponseData) : MarsManifestData()
    data class Error(val error: Throwable) : MarsManifestData()
    data class Loading(val progress: Int?) : MarsManifestData()
}

package ru.varasoft.pictureoftheday.model.pod

interface IPODCache {
    fun getPOD(date: String) : PODServerResponseData
    fun insertPOD(pod: PODServerResponseData, date: String)
}
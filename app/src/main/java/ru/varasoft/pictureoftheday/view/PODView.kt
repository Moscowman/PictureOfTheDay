package ru.varasoft.pictureoftheday.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.varasoft.pictureoftheday.model.pod.PODServerResponseData
import ru.varasoft.pictureoftheday.model.pod.PictureOfTheDayData

interface PODView : MvpView {
    @AddToEndSingle
    fun displayPicture(podData: PODServerResponseData)
}
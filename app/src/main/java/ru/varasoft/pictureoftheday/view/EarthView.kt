package ru.varasoft.pictureoftheday.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.varasoft.pictureoftheday.model.pod.PODServerResponseData
import ru.varasoft.pictureoftheday.model.pod.PictureOfTheDayData

@StateStrategyType(AddToEndSingleStrategy::class)
interface EarthView : MvpView {
    fun displayPicture(url: String)
}
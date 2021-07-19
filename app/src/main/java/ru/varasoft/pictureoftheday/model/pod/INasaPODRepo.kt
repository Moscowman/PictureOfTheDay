package ru.varasoft.pictureoftheday.model.pod

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single

interface INasaPODRepo {
    fun getPicture(date: String): @NonNull Single<PODServerResponseData>
}
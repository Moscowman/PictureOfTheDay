package ru.varasoft.pictureoftheday.model.pod

import android.content.Context
import io.reactivex.rxjava3.core.Single
import ru.varasoft.pictureoftheday.AndroidNetworkStatus
import ru.varasoft.pictureoftheday.model.RetrofitImpl

class NasaPODRepo(
    private val retrofitImpl: RetrofitImpl,
    private val podCache: RoomPODCache,
    private val context: Context
) :
    INasaPODRepo {
    override fun getPicture(apiKey: String, date: String) =
        AndroidNetworkStatus(context).isOnlineSingle()
            .flatMap { isOnline ->
                if (isOnline) {
                    val retrofit = retrofitImpl.getPODRetrofitImpl()
                    retrofit.getPictureOfTheDay(apiKey, "true", date)
                        .flatMap { pod ->
                            Single.fromCallable {
                                podCache.insertPOD(pod, date)
                                pod
                            }
                        }
                } else {
                    Single.fromCallable {
                        podCache.getPOD(date)
                    }
                }
            }
}
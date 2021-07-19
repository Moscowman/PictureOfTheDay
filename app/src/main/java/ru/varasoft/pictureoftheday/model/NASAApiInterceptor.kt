package ru.varasoft.pictureoftheday.model

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

object NASAApiInterceptor  : Interceptor {

    val apiKey = "DEMO_KEY"

    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(
            chain.request()
                .newBuilder()
                .header("api_key", apiKey)
                .build()
        )
}
package ru.mozgolom112.weatherapp.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import ru.mozgolom112.weatherapp.utils.consts.APP_ID

object RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
            .header("X-Yandex-API-Key", APP_ID)
            .build()

        Log.i("RequestInterceptor", "${request.url()}")
        return chain.proceed(request)
    }

}
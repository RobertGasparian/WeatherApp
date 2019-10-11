package com.example.weatherapp.networking.interceptors

import com.example.weatherapp.app.Constants.API_KEY
import com.example.weatherapp.app.Constants.APP_ID_KEY
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder().addQueryParameter(APP_ID_KEY, API_KEY).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
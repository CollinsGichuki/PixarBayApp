package com.example.pixarbayapp.api

import android.util.Log
import com.example.pixarbayapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val apiKey = BuildConfig.API_KEY

        //Create a new request with the key in it
        val newUrl = originalRequest.url.newBuilder().addQueryParameter("key", apiKey).build()
        Log.d("API_STUFF", "The url: $newUrl")

        //Build a new request and proceed with the network call
        val finalRequest = chain.request().newBuilder().url(newUrl).build()
        return chain.proceed(finalRequest)
    }
}
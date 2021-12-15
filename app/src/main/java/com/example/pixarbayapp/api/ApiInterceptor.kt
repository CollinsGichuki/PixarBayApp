package com.example.pixarbayapp.api

import com.example.pixarbayapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

//We intercept the netork call to add the key value for every network call
class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        //Get the api key from BuildConfig
        val apiKey = BuildConfig.API_KEY

        //Create a new request with the api key in it
        val newUrl = originalRequest.url.newBuilder().addQueryParameter("key", apiKey).build()

        //Build a new request and proceed with the network call
        val finalRequest = chain.request().newBuilder().url(newUrl).build()
        return chain.proceed(finalRequest)
    }
}
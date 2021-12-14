package com.example.pixarbayapp.api

import com.example.pixarbayapp.data.ImageResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {
    //Base Url
    companion object {
        const val BASE_URL = "https://pixabay.com/api/"
    }

    //Get the searched Image
    @GET(".")
    suspend fun searchImages(
        //Search parameter
        @Query("q") q: String,
    ): ImageResult
}
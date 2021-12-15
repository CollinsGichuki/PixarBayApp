package com.example.pixarbayapp.api

import com.example.pixarbayapp.data.ImageResult
import retrofit2.http.GET
import retrofit2.http.Query

//This is the interface for the network calls to the api
interface ImagesApi {
    //Base Url
    companion object {
        const val BASE_URL = "https://pixabay.com/api/"
    }

    //Get the searched Image
    @GET(".")//Since there is no endpoint
    suspend fun searchImages(
        //Search parameter
        @Query("q") q: String,
    ): ImageResult
}
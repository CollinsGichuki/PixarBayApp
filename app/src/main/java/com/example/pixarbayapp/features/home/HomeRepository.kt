package com.example.pixarbayapp.features.home

import android.util.Log
import com.example.pixarbayapp.api.ImagesApi
import com.example.pixarbayapp.data.ImageResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val api: ImagesApi
) {
    //The data from the PagingSource
    suspend fun getSearchResults(query: String): ImageResult {
        Log.d("API_STUFF", "Making network call")
        return api.searchImages(query)
    }
}
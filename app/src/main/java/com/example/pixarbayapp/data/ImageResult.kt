package com.example.pixarbayapp.data

//This is the result of the network call to the api
data class ImageResult(
    val total: Int,
    val totalHits: Int,
    val hits: List<Image>
)
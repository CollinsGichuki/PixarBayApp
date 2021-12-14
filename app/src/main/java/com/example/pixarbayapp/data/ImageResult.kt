package com.example.pixarbayapp.data

data class ImageResult(
    val total: Int,
    val totalHits: Int,
    val hits: List<Image>
)
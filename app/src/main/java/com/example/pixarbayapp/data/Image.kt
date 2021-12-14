package com.example.pixarbayapp.data

data class Image(
    val id: Int,
    val largeImageURL: String,
    val views: Int,
    val downloads: Int,
    val collections: Int,
    val likes: Int,
    val comments: Int,
    val user: String,
)
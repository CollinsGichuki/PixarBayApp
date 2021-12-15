package com.example.pixarbayapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//We use parcelize to able to navigate between the two fragments with the Image
//This class is used a model for the data from the api and as a db table for local storage
@Parcelize
@Entity(tableName = "images")
data class Image(
    @PrimaryKey val id: Int, //Use Id as the primary key since it is unique
    val largeImageURL: String,
    val views: Int,
    val downloads: Int,
    val collections: Int,
    val likes: Int,
    val comments: Int,
    val user: String,
) : Parcelable
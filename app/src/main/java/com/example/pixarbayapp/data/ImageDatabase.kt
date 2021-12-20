package com.example.pixarbayapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

//Use abstract class not interface because we need to extend RoomDatabase
@Database(entities = [Image::class], version = 1)
abstract class ImageDatabase : RoomDatabase() {

    //This provides the dao
    abstract fun imageDao(): ImageDao
}
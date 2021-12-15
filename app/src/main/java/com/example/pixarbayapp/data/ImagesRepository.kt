package com.example.pixarbayapp.data

import androidx.room.withTransaction
import com.example.pixarbayapp.api.ImagesApi
import com.example.pixarbayapp.util.networkBoundResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesRepository @Inject constructor(
    private val api: ImagesApi,
    private val db: ImageDatabase
) {

    //The dao
    private val dao = db.imageDao()

    //Get the data
    fun getImages(searchQuery: String) = networkBoundResource(
        //First get the data from the db
        query = {
            dao.getAllImages()
        },
        //Make the network request
        fetch = {
            val images = api.searchImages(searchQuery)
            images.hits//Get the list of images from the result
        },
        //Update the contents of the db
        saveFetchResult = { images ->
            //All the db transactions take place or none
            db.withTransaction {
                //Update the database content
                dao.deleteAllImages()
                dao.insertImage(images)
            }
        }
    )
}
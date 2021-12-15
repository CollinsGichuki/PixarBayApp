package com.example.pixarbayapp.util

//Generic type T enables this class to be used with any type of data
sealed class Resource<T>(
    //Pass the data and throwable so that it is easier to use the Resource class
    val data: T? = null,
    val throwable: Throwable? = null
) {

    class Success<T>(data: T) : Resource<T>(data)

    //We can show the cached data in loading state, but to simplify it, we don't
    class Loading<T>(data: T? = null) : Resource<T>(data)

    //We can show the cached data when there is an error, but to simplify it, we don't
    class Error<T>(throwable: Throwable, data: T? = null) : Resource<T>(data, throwable)
}
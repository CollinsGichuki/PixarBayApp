package com.example.pixarbayapp.util

import kotlinx.coroutines.flow.*

/*
inline makes the higher order functions more efficient by not having to create unnecessary objects for these functions
crossinline makes sure we can't call return in the functions passed to the NetworkBoundResource
The generic types makes the function reusable with any data type
*/
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,//Get a stream of the result from the db
    crossinline fetch: suspend () -> RequestType, //The list of Images from the network call
    crossinline saveFetchResult: suspend (RequestType) -> Unit, //Store the data from the network call to the db
    //checks if the data in the db is stale and if we should make the network call
    //If no value passed, make the network call
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    //First get the data from the db
    val data = query().first()//Collect only once from the flow

    //Check if should fetch the data from the api
    val imagesFlow = if (shouldFetch(data)) {
        //Emit load state to show spinner in the UI
        //Emit the cached data while fetching the new data from the api
        emit(Resource.Loading(data))

        try {
            //Fetch the data from the api and save it to the db
            saveFetchResult(fetch())
            //Get the data stream from the db
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            //Emit the old data from the db and the error caught
            query().map { Resource.Error(throwable, it) }
        }
    } else {
        //Get the current cached data
        query().map { Resource.Success(it) }
    }

    //Emit the data streams from the flow
    emitAll(imagesFlow)
}
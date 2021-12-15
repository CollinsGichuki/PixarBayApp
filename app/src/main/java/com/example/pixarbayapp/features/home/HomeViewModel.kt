package com.example.pixarbayapp.features.home

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.example.pixarbayapp.data.ImagesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

//Hilt doesn't support AssistedInjection for viewModels
//We have to create a factory for the viewModel so as to create a viewModel with the dependencies we want
class HomeViewModel @AssistedInject constructor(
    repository: ImagesRepository,
    @Assisted private val defaultQuery: String,
    @Assisted private val state: SavedStateHandle // Saves data and retrieves them in process death scenarios
) : ViewModel() {
    //Use the factory to create our viewModel
    @AssistedFactory
    interface HomeViewModelFactory {
        fun create(defaultQuery: String, handle: SavedStateHandle): HomeViewModel
    }

    // The last query wil be saved to the state
    // Load the last query from saved state
    // Or if none(first time launching the app), load the default query
    private val currentQuery = state.getLiveData(CURRENT_QUERY, defaultQuery)

    //Called from the fragment
    fun searchImages(query: String) {
        //Equate value entered in the fragment with the value to be searched
        currentQuery.value = query
    }

    //Collect from the flow and cast the data to livedata
    //SwitchMap updates the currentQuery value to use the latest value
    //Livedata makes using the flow in the UI layer very easy
    //val imagesResult = currentQuery.value?.let { repository.getImages(it) }?.asLiveData()
    val imagesResult = currentQuery.switchMap { queryString ->
        repository.getImages(queryString).asLiveData()
    }

    //We use companion object to tie these values to the ViewModel class and not its instances
    companion object {
        private const val CURRENT_QUERY = "current_query"

        //Provides our factory for the HomeViewModel
        fun provideFactory(
            assistedFactory: HomeViewModelFactory,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
            defaultQuery: String = "dogs"
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return assistedFactory.create(defaultQuery, handle) as T
                }
            }
    }
}
package com.example.pixarbayapp.features.home

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.example.pixarbayapp.data.ImageResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

//The viewModel has special treatment when annotated with @HiltViewModel
//@HiltViewModel
class HomeViewModel @AssistedInject constructor(
    repository: HomeRepository,
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

    private val _imagesResult = MutableLiveData<ImageResult>()

    //Expose LiveData to the fragment
    val imagesResult: LiveData<ImageResult> = _imagesResult

    init {
        viewModelScope.launch {
            //Get the default query from the currentQuery as a String
            val images = currentQuery.value?.let { repository.getSearchResults(it) }

            //Update the live data with the result from the network call
            _imagesResult.value = images!!
        }
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"

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
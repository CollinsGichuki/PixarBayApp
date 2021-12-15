package com.example.pixarbayapp.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pixarbayapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var homeViewModelFactory: HomeViewModel.HomeViewModelFactory

    //Use the factory to create an instance of HomeviewModel
    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModel.provideFactory(homeViewModelFactory, this, arguments, "dogs")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imagesAdapter = ImagesAdapter()

        //OnClickListener
        imagesAdapter.setOnItemClickListener { selectedImage ->
            //Navigate to the details fragment with the selected image
            val directions =
                HomeFragmentDirections.actionHomeFragmentToImageDetailsFragment(selectedImage)
            findNavController().navigate(directions)
        }

        //Set up the recyclerview
        binding.apply {
            imageRv.apply {
                adapter = imagesAdapter
                layoutManager = LinearLayoutManager(context)
            }

            //SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // Start the query search
                    if (query != null) {
                        //Make the network call
                        //Format the query
                        val formattedString = query.lowercase()
                        homeViewModel.searchImages(formattedString)
                        searchView.clearFocus()//Hide the keyboard when we submit the search
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // Do nothing while the user is still typing
                    return true
                }

            })

            //Observe the images liveData
            homeViewModel.imagesResult.observe(viewLifecycleOwner) { resource ->
                //Update the data for the adapter
                imagesAdapter.submitList(resource.data)

                //Progress bar is visible if the resource emitted is Loading and there isn't any data
                progressBar.isVisible =
                    resource is com.example.pixarbayapp.util.Resource.Loading && resource.data.isNullOrEmpty()

                //Error text is visible if the resource emitted is Error and there isn't any data
                errorTv.isVisible =
                    resource is com.example.pixarbayapp.util.Resource.Error && resource.data.isNullOrEmpty()

                //Get the error from teh Resource
                errorTv.text = resource.throwable?.localizedMessage
            }
        }


    }
}
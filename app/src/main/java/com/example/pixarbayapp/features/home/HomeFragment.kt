package com.example.pixarbayapp.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pixarbayapp.data.ImageResult
import com.example.pixarbayapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    //private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var homeViewModelFactory: HomeViewModel.HomeViewModelFactory

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

        //
        val imagesAdapter = ImagesAdapter()

        //Set up the recyclerview
        binding.apply {
            imageRv.apply {
                adapter = imagesAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }

        //Observe the images liveData
        homeViewModel.imagesResult.observe(viewLifecycleOwner) { imagesResult: ImageResult ->
            //Update the data for the adapter
            imagesAdapter.submitList(imagesResult.hits)
        }
    }
}
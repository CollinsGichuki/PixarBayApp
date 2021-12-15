package com.example.pixarbayapp.features.imageDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pixarbayapp.databinding.FragmentImageDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageDetailsFragment : Fragment() {
    private lateinit var binding: FragmentImageDetailsBinding

    //Get the selectedImage
    private val args by navArgs<ImageDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            //The image
            Glide.with(this@ImageDetailsFragment)
                .load(args.selectedImage.largeImageURL)
                .centerCrop()
                .into(binding.selectedImage)
            //The owner of the image
            userNameTv.text = args.selectedImage.user
            //The number of views
            viewsValueTv.text = args.selectedImage.views.toString()
            //The number of downloads
            downloadsValueTv.text = args.selectedImage.views.toString()
            //The number of collections
            collectionsValueTv.text = args.selectedImage.collections.toString()
            //The number of likes
            likesValueTv.text = args.selectedImage.likes.toString()
            //The number of comments
            commentsValueTv.text = args.selectedImage.comments.toString()
        }
    }
}
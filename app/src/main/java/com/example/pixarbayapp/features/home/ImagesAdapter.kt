package com.example.pixarbayapp.features.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pixarbayapp.data.Image
import com.example.pixarbayapp.databinding.ImageRecyclerViewItemBinding

class ImagesAdapter : ListAdapter<Image, ImagesAdapter.ImagesViewHolder>(ImageComparator()) {

    class ImagesViewHolder(private val binding: ImageRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(image: Image) {
            binding.apply {
                //Bind the image url to the Image view
                Glide.with(itemView)
                    .load(image.largeImageURL)
                    .centerCrop()
                    .into(binding.imageView)

                //Bind the username to the textView
                binding.userNameTv.text = image.user
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        //Create the View
        val binding =
            ImageRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val currentItem = getItem(position)
        //If the current item isn't null, bind the data to the view
        if (currentItem != null) {
            holder.binding(currentItem)

            holder.itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(currentItem)
                }
            }
        }
    }

    //Interface for the onClickListener
    private var onItemClickListener: ((Image) -> Unit)? = null

    //Method to be used in the fragment for the listener implementation
    fun setOnItemClickListener(listener: (Image) -> Unit) {
        this.onItemClickListener = listener
    }

    //DiffUtil calculates the differences between two Images
    class ImageComparator : DiffUtil.ItemCallback<Image>() {
        //Check if two Images are the same
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id //Id is unique for each image
        }

        //Check if the two items have the same data
        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.largeImageURL == newItem.largeImageURL //ImageURL should be different for each Image
        }
    }
}
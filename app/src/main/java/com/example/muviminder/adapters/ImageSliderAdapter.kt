package com.example.muviminder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.muviminder.R
import com.example.muviminder.adapters.ImageSliderAdapter.ImageSliderViewHolder
import com.example.muviminder.databinding.ItemContainerSliderImageBinding

class ImageSliderAdapter(private val sliderImages: Array<String>) :
    RecyclerView.Adapter<ImageSliderViewHolder>() {
    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val sliderImageBinding = DataBindingUtil.inflate<ItemContainerSliderImageBinding>(
            layoutInflater!!, R.layout.item_container_slider_image, parent, false
        )
        return ImageSliderViewHolder(sliderImageBinding)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        holder.bindSliderImage(sliderImages[position])
    }

    override fun getItemCount(): Int {
        return sliderImages.size
    }

    class ImageSliderViewHolder(private val itemContainerSliderImageBinding: ItemContainerSliderImageBinding) :
        RecyclerView.ViewHolder(
            itemContainerSliderImageBinding.root
        ) {
        fun bindSliderImage(imageURL: String?) {
            itemContainerSliderImageBinding.imageURL = imageURL
        }
    }
}
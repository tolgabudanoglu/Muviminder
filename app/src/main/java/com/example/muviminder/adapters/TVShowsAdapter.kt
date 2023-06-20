package com.example.muviminder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.muviminder.R
import com.example.muviminder.adapters.TVShowsAdapter.TVShowViewHolder
import com.example.muviminder.databinding.ItemContaineTvShowBinding
import com.example.muviminder.models.TvShow

class TVShowsAdapter(private val tvShows: List<TvShow>) : RecyclerView.Adapter<TVShowViewHolder>() {
    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val tvShowBinding = DataBindingUtil.inflate<ItemContaineTvShowBinding>(
            layoutInflater!!, R.layout.item_containe_tv_show, parent, false
        )
        return TVShowViewHolder(tvShowBinding)
    }

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        holder.bindTVShow(tvShows[position])
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }

    class TVShowViewHolder(private val itemContaineTvShowBinding: ItemContaineTvShowBinding) :
        RecyclerView.ViewHolder(
            itemContaineTvShowBinding.root
        ) {
        fun bindTVShow(tvShow: TvShow?) {
            itemContaineTvShowBinding.tvShow = tvShow
            itemContaineTvShowBinding.executePendingBindings()
        }
    }
}
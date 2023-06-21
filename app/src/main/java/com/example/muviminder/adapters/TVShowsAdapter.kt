package com.example.muviminder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.muviminder.R
import com.example.muviminder.databinding.ItemContaineTvShowBinding
import com.example.muviminder.listeners.TVShowsListener
import com.example.muviminder.models.TvShow

class TVShowsAdapter(private val tvShows: MutableList<TvShow>,private val tvShowsListener: TVShowsListener) : RecyclerView.Adapter<TVShowsAdapter.TVShowViewHolder?>() {

    private var layoutInflater: LayoutInflater? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val tvShowBinding: ItemContaineTvShowBinding = DataBindingUtil.inflate(
            layoutInflater!!, R.layout.item_containe_tv_show, parent, false
        )
        return TVShowViewHolder(tvShowBinding)
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }
    override fun onBindViewHolder(holder: TVShowsAdapter.TVShowViewHolder, position: Int) {
        holder.bindTVShow(tvShows[position])
    }



    inner class TVShowViewHolder(itemContaineTvShowBinding: ItemContaineTvShowBinding) :
        RecyclerView.ViewHolder(itemContaineTvShowBinding.getRoot()) {
        private val itemContaineTvShowBinding: ItemContaineTvShowBinding

        init {
            this.itemContaineTvShowBinding = itemContaineTvShowBinding
        }

        fun bindTVShow(tvShow: TvShow) {
            itemContaineTvShowBinding.tvShow = tvShow
            itemContaineTvShowBinding.executePendingBindings()
            itemContaineTvShowBinding.root
                .setOnClickListener { tvShowsListener.onTVShowClicked(tvShow) }
        }
    }
}
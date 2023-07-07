package com.example.muviminder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.muviminder.R
import com.example.muviminder.adapters.EpisodesAdapter.EpisodeViewHolder
import com.example.muviminder.databinding.ItemContainerEpisodeBinding
import com.example.muviminder.models.Episode

class EpisodesAdapter(private val episodes: List<Episode>) :
    RecyclerView.Adapter<EpisodeViewHolder>() {
    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val itemContainerEpisodeBinding = DataBindingUtil.inflate<ItemContainerEpisodeBinding>(
            layoutInflater!!, R.layout.item_container_episode, parent, false
        )
        return EpisodeViewHolder(itemContainerEpisodeBinding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bindEpisode(episodes[position])
    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    class EpisodeViewHolder(private val itemContainerEpisodeBinding: ItemContainerEpisodeBinding) :
        RecyclerView.ViewHolder(
            itemContainerEpisodeBinding.root
        ) {
        fun bindEpisode(episode: Episode) {
            var tittle = "S"
            var season = episode.season
            if (season!!.length == 1) {
                season = "0$season"
            }
            var episodeNumber = episode.episode
            if (episodeNumber!!.length == 1) {
                episodeNumber = "0$episodeNumber"
            }
            episodeNumber = "E$episodeNumber"
            tittle = tittle + season + episodeNumber
            itemContainerEpisodeBinding.tittle = tittle
            itemContainerEpisodeBinding.name = episode.name
            itemContainerEpisodeBinding.airDate = episode.airDate
        }
    }
}
package com.example.muviminder.listeners

import com.example.muviminder.models.TvShow

interface TVShowsListener {
    fun onTVShowClicked(tvShow: TvShow?)
}
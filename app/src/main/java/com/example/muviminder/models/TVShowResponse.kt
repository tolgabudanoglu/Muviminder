package com.example.muviminder.models

import com.google.gson.annotations.SerializedName

data class TVShowResponse (

    @SerializedName("page")
    val page: Int?,
    @SerializedName("pages")
    val pages: Int?,
    @SerializedName("tv_shows")
    val tvShows: List<TvShow>?
)
package com.example.muviminder.models

import com.google.gson.annotations.SerializedName

data class TVShowDetailsResponse(

    @SerializedName("tvShow")
    val tvShowDetails: TVShowDetails?,
)

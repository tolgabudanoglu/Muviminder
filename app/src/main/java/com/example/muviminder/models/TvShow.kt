package com.example.muviminder.models


import com.google.gson.annotations.SerializedName

data class TvShow(
    @SerializedName("country")
    val country: String?,

    @SerializedName("id")
    val id: Int?,

    @SerializedName("image_thumbnail_path")
    val imageThumbnailPath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("network")
    val network: String?,

    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("status")
    val status: String?
)
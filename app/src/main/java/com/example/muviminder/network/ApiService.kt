package com.example.muviminder.network

import com.example.muviminder.models.TVShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("most-popular")
    fun getMostPopularTVShows(@Query("page") page: Int): Call<TVShowResponse?>?


}
package com.example.muviminder.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.muviminder.models.TVShowResponse
import com.example.muviminder.network.ApiClient.retrofit
import com.example.muviminder.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MostPopularTVShowRepository {
    private val apiService: ApiService

    init {
        apiService = retrofit!!.create(ApiService::class.java)
    }

    fun getMostPopularTVShow(page: Int): LiveData<TVShowResponse?> {
        val data = MutableLiveData<TVShowResponse?>()
        apiService.getMostPopularTVShows(page)!!.enqueue(object : Callback<TVShowResponse?> {
            override fun onResponse(
                call: Call<TVShowResponse?>,
                response: Response<TVShowResponse?>
            ) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<TVShowResponse?>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }
}
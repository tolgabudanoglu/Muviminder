package com.example.muviminder.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.muviminder.models.TVShowDetailsResponse
import com.example.muviminder.network.ApiClient.retrofit
import com.example.muviminder.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TVShowDetailsRepository {
    private val apiService: ApiService = retrofit!!.create(ApiService::class.java)

    fun getTVShowDetails(tvShowId: String?): LiveData<TVShowDetailsResponse?> {
        val data = MutableLiveData<TVShowDetailsResponse?>()
        apiService.getTVShowDetails(tvShowId!!).enqueue(object : Callback<TVShowDetailsResponse?> {
            override fun onResponse(
                call: Call<TVShowDetailsResponse?>,
                response: Response<TVShowDetailsResponse?>
            ) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<TVShowDetailsResponse?>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }
}
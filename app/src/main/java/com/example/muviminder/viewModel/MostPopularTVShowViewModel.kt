package com.example.muviminder.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.muviminder.models.TVShowResponse
import com.example.muviminder.repositories.MostPopularTVShowRepository

class MostPopularTVShowViewModel : ViewModel() {
    private val mostPopularTVShowRepository: MostPopularTVShowRepository =
        MostPopularTVShowRepository()

    fun getMostPopularTVShows(page: Int): LiveData<TVShowResponse?> {
        return mostPopularTVShowRepository.getMostPopularTVShow(page)
    }
}
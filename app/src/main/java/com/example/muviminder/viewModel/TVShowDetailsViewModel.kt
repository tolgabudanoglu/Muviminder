package com.example.muviminder.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.muviminder.models.TVShowDetailsResponse
import com.example.muviminder.repositories.TVShowDetailsRepository

class TVShowDetailsViewModel : ViewModel() {
    private val tvShowDetailsRepository: TVShowDetailsRepository = TVShowDetailsRepository()

    fun getTVShowDetails(tvShowId: String?): LiveData<TVShowDetailsResponse?> {
        return tvShowDetailsRepository.getTVShowDetails(tvShowId)
    }
}
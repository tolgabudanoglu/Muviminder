package com.example.muviminder.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.muviminder.R
import com.example.muviminder.models.TVShowResponse
import com.example.muviminder.viewModel.MostPopularTVShowViewModel

class HomeScreen : AppCompatActivity() {
    private var viewModel: MostPopularTVShowViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        viewModel = ViewModelProvider(this).get(
            MostPopularTVShowViewModel::class.java
        )
        mostPopularTVShows
    }

    private val mostPopularTVShows: Unit
        private get() {
            viewModel!!.getMostPopularTVShows(0).observe(
                this,
                Observer<TVShowResponse?> { mostPopularTVShowsResponse: TVShowResponse? ->
                    Toast.makeText(
                        applicationContext,
                        "Total Pages " + mostPopularTVShowsResponse?.pages,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
}
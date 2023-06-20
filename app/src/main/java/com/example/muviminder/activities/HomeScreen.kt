package com.example.muviminder.activities


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.muviminder.R
import com.example.muviminder.adapters.TVShowsAdapter
import com.example.muviminder.databinding.ActivityHomeScreenBinding

import com.example.muviminder.models.TVShowResponse
import com.example.muviminder.models.TvShow
import com.example.muviminder.viewModel.MostPopularTVShowViewModel

class HomeScreen : AppCompatActivity() {
    private var viewModel: MostPopularTVShowViewModel? = null
    private var activityMainBinding : ActivityHomeScreenBinding? = null
    private val tvShows: MutableList<TvShow> = ArrayList()
    private var tvShowsAdapter: TVShowsAdapter? = null
    private var currentPage = 1
    private var totalAvailablePages = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_home_screen)
        viewModel = ViewModelProvider(this).get(
            MostPopularTVShowViewModel::class.java
        )
        mostPopularTVShows
        doInitialization()
    }


    private fun doInitialization() {
        activityMainBinding?.tvShowsRecyclerView?.setHasFixedSize(true)
        viewModel = ViewModelProvider(this).get<MostPopularTVShowViewModel>(
            MostPopularTVShowViewModel::class.java
        )
        tvShowsAdapter = TVShowsAdapter(tvShows)
        activityMainBinding?.tvShowsRecyclerView?.setAdapter(tvShowsAdapter)
        activityMainBinding?.tvShowsRecyclerView?.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!activityMainBinding!!.tvShowsRecyclerView.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1
                        mostPopularTVShows
                        Log.d("TAG", totalAvailablePages.toString())
                        Log.e("asad", currentPage.toString())
                    }
                }
            }
        })
        mostPopularTVShows
    }




    private val mostPopularTVShows: Unit
        private get() {
            toggleLoading()
            viewModel!!.getMostPopularTVShows(currentPage)
                .observe(this, Observer { mostPopularTVShowResponse: TVShowResponse? ->
                    toggleLoading()
                    if (mostPopularTVShowResponse != null) {
                        totalAvailablePages = mostPopularTVShowResponse.pages!!
                        if (mostPopularTVShowResponse.tvShows != null) {
                            var oldCount = tvShows.size
                            tvShows.addAll(mostPopularTVShowResponse.tvShows)
                            tvShowsAdapter?.notifyItemRangeInserted(oldCount,tvShows.size)
                        }
                    }
                })
        }




    private fun toggleLoading() {
        if (currentPage == 1) {
            activityMainBinding?.isLoading =
                !(activityMainBinding?.isLoading == true && activityMainBinding?.isLoading == true)
        } else {
            activityMainBinding?.isLoadingMore = (activityMainBinding?.isLoadingMore
                ?: activityMainBinding?.isLoadingMore) != true
        }
    }
}
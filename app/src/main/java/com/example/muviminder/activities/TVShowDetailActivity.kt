package com.example.muviminder.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.muviminder.R
import com.example.muviminder.adapters.ImageSliderAdapter
import com.example.muviminder.databinding.ActivityTvshowDetailBinding
import com.example.muviminder.models.TVShowDetailsResponse
import com.example.muviminder.viewModel.TVShowDetailsViewModel

class TVShowDetailActivity : AppCompatActivity() {
    private var activityTvshowDetailBinding: ActivityTvshowDetailBinding? = null
    private var tvShowDetailsViewModel: TVShowDetailsViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTvshowDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_tvshow_detail)
        doInitialization()
    }

    private fun doInitialization() {
        tvShowDetailsViewModel = ViewModelProvider(this).get(
            TVShowDetailsViewModel::class.java
        )
        tVShowDetails
    }

    private val tVShowDetails: Unit
        get() {
            activityTvshowDetailBinding!!.isLoading = true
            val tvShowId = intent.getIntExtra("id", -1).toString()
            tvShowDetailsViewModel!!.getTVShowDetails(tvShowId).observe(
                this, Observer { tvShowDetails: TVShowDetailsResponse? ->
                    activityTvshowDetailBinding!!.isLoading = false
                    if(tvShowDetails?.tvShowDetails != null){
                        if(tvShowDetails.tvShowDetails.pictures != null){
                            loadImageSlider(tvShowDetails.tvShowDetails.pictures)

                        }

                    }
                }
            )
        }
    private fun loadImageSlider(sliderImages: List<String>?) {
        activityTvshowDetailBinding?.sliderViewPager?.offscreenPageLimit = 1
        activityTvshowDetailBinding?.sliderViewPager?.adapter = ImageSliderAdapter(sliderImages)
        activityTvshowDetailBinding?.sliderViewPager?.visibility = View.VISIBLE
        activityTvshowDetailBinding?.viewFadingEdge?.visibility = View.VISIBLE
    }

}
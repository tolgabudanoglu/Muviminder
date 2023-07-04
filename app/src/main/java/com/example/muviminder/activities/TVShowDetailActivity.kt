package com.example.muviminder.activities

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
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
        activityTvshowDetailBinding?.imageBack?.setOnClickListener {
            onBackPressed()
        }
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
                        activityTvshowDetailBinding?.tvShowImageUrl = tvShowDetails.tvShowDetails.imagePath
                        activityTvshowDetailBinding?.imageTvShow?.visibility = View.VISIBLE
                        loadBasicTVShowDetails()


                    }
                }
            )
        }
    private fun loadImageSlider(sliderImages: List<String>?) {
        activityTvshowDetailBinding?.sliderViewPager?.offscreenPageLimit = 1
        activityTvshowDetailBinding?.sliderViewPager?.adapter = ImageSliderAdapter(sliderImages)
        activityTvshowDetailBinding?.sliderViewPager?.visibility = View.VISIBLE
        activityTvshowDetailBinding?.viewFadingEdge?.visibility = View.VISIBLE
        setupSliderIndicator(sliderImages!!.size)
        activityTvshowDetailBinding?.sliderViewPager?.registerOnPageChangeCallback(object :
            OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentSliderIndicator(position)
            }
        }
        )
    }

    private fun setupSliderIndicator(count: Int){
        val indicators = arrayOfNulls<ImageView>(count)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in 1 until indicators.size) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.background_slider_indicator_inactive
                )
            )
            indicators[i]!!.layoutParams = layoutParams
            activityTvshowDetailBinding?.layoutSliderIndicators?.addView(indicators[i])

        }
        activityTvshowDetailBinding?.layoutSliderIndicators?.visibility = View.VISIBLE
        setCurrentSliderIndicator(0)


    }
    private fun setCurrentSliderIndicator(position: Int) {
        val childCount: Int = activityTvshowDetailBinding?.layoutSliderIndicators!!.childCount
        for (i in 0 until childCount) {
            val imageView =
                activityTvshowDetailBinding?.layoutSliderIndicators!!.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.background_slider_indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.background_slider_indicator_inactive
                    )
                )
            }
        }
    }
    private fun loadBasicTVShowDetails(){
        activityTvshowDetailBinding?.tvShowName=intent.getStringExtra("name")
        activityTvshowDetailBinding?.networkCountry = intent.getStringExtra("network") + "(" + intent.getStringExtra("country") + ")"
        activityTvshowDetailBinding?.status=intent.getStringExtra("status")
        activityTvshowDetailBinding?.startedDate = intent.getStringExtra("startDate")
        activityTvshowDetailBinding?.tvName?.visibility = View.VISIBLE
        activityTvshowDetailBinding?.tvNetworkCountry?.visibility= View.VISIBLE
        activityTvshowDetailBinding?.tvStatus?.visibility = View.VISIBLE
        activityTvshowDetailBinding?.tvStarted?.visibility = View.VISIBLE
    }


}
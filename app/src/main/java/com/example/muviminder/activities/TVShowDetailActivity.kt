package com.example.muviminder.activities

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.ContentView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.muviminder.R
import com.example.muviminder.adapters.EpisodesAdapter
import com.example.muviminder.adapters.ImageSliderAdapter
import com.example.muviminder.databinding.ActivityTvshowDetailBinding
import com.example.muviminder.databinding.LayoutEpisodesBottomSheetBinding
import com.example.muviminder.models.Episode
import com.example.muviminder.models.TVShowDetailsResponse
import com.example.muviminder.viewModel.TVShowDetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.lang.Double
import java.util.*

class TVShowDetailActivity : AppCompatActivity() {
    private var activityTvshowDetailBinding: ActivityTvshowDetailBinding? = null
    private var tvShowDetailsViewModel: TVShowDetailsViewModel? = null
    private var episodesBottomSheetDialog: BottomSheetDialog? = null
    private var layoutEpisodesBottomSheetBinding : LayoutEpisodesBottomSheetBinding? = null
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
                        activityTvshowDetailBinding!!.description = tvShowDetails.tvShowDetails.description?.let {
                            HtmlCompat.fromHtml(
                                it,
                                HtmlCompat.FROM_HTML_MODE_LEGACY


                            ).toString()
                        }
                        activityTvshowDetailBinding?.tvDescription?.visibility = View.VISIBLE
                        activityTvshowDetailBinding?.tvReadMore?.visibility = View.VISIBLE
                        activityTvshowDetailBinding?.tvReadMore?.setOnClickListener {
                            if (activityTvshowDetailBinding?.tvReadMore?.text.toString().equals("Daha fazla oku")){
                                activityTvshowDetailBinding?.tvDescription?.maxLines= Int.MAX_VALUE
                                activityTvshowDetailBinding?.tvDescription?.ellipsize = null
                                activityTvshowDetailBinding?.tvReadMore?.text = getString(R.string.read_Less)
                            }else{
                                activityTvshowDetailBinding?.tvDescription?.maxLines = 3
                                activityTvshowDetailBinding?.tvDescription?.ellipsize = TextUtils.TruncateAt.END
                                activityTvshowDetailBinding?.tvReadMore?.text = getString(R.string.read_More)

                            }
                        }
                        activityTvshowDetailBinding?.rating = String.format(Locale.getDefault(),"%.2f",
                            tvShowDetails.tvShowDetails.rating?.let { Double.parseDouble(it) }
                        )

                        if (tvShowDetails.tvShowDetails.genres !=null){
                            activityTvshowDetailBinding?.genre = tvShowDetails.tvShowDetails.genres[0]
                        }else{
                            activityTvshowDetailBinding?.genre = "N/A"
                        }
                        activityTvshowDetailBinding?.runtime = tvShowDetails.tvShowDetails.runtime + " Min"
                        activityTvshowDetailBinding?.viewDivider1?.visibility = View.VISIBLE
                        activityTvshowDetailBinding?.layoutMisc?.visibility = View.VISIBLE
                        activityTvshowDetailBinding?.viewDivider2?.visibility = View.VISIBLE
                        activityTvshowDetailBinding?.btnWebsite?.setOnClickListener {

                            val reurl= (Uri.parse(tvShowDetails.tvShowDetails.url))
                            var intent = Intent(Intent.ACTION_VIEW,reurl)
                            startActivity(intent)
                        }
                        activityTvshowDetailBinding?.btnWebsite?.visibility = View.VISIBLE
                        activityTvshowDetailBinding?.btnEpisodes?.visibility = View.VISIBLE

                        activityTvshowDetailBinding?.btnEpisodes?.setOnClickListener {
                            if (episodesBottomSheetDialog == null){
                                episodesBottomSheetDialog = BottomSheetDialog(this@TVShowDetailActivity)
                                layoutEpisodesBottomSheetBinding = DataBindingUtil.inflate(
                                    LayoutInflater.from(this@TVShowDetailActivity),
                                    R.layout.layout_episodes_bottom_sheet,
                                    findViewById(R.id.episodesContainer),
                                    false
                                )
                                episodesBottomSheetDialog!!.setContentView(layoutEpisodesBottomSheetBinding!!.root)
                                layoutEpisodesBottomSheetBinding?.episodesRv?.adapter =
                                    tvShowDetails.tvShowDetails.episodes?.let { it1 ->
                                        EpisodesAdapter(
                                            it1
                                        )
                                    }
                                layoutEpisodesBottomSheetBinding!!.textTittle.setText(
                                    String.format("Episodes | %s",intent.getStringExtra("name"))
                                )
                                layoutEpisodesBottomSheetBinding!!.imageClose.setOnClickListener {
                                    episodesBottomSheetDialog!!.dismiss()
                                }

                                val frameLayout = episodesBottomSheetDialog!!.findViewById<FrameLayout>(
                                    com.google.android.material.R.id.design_bottom_sheet
                                )
                                if (frameLayout != null) {
                                    val bottomSheetBehavior = BottomSheetBehavior.from<View>(frameLayout)
                                    bottomSheetBehavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
                                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                                }
                                episodesBottomSheetDialog!!.show()








                            }
                        }



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
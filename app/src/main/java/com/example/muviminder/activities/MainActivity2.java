/*package com.example.muviminder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.muviminder.R;
import com.example.muviminder.adapters.TVShowsAdapter;
import com.example.muviminder.databinding.ActivityMainBinding;
import com.example.muviminder.models.TvShow;
import com.example.muviminder.viewModel.MostPopularTVShowViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private MostPopularTVShowViewModel viewModel;
    private List<TvShow> tvShows = new ArrayList<>();
    private TVShowsAdapter tvShowsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    private void getMostPopularTVShows(){
        activityMainBinding.setIsLoading(true);
        viewModel.getMostPopularTVShows(0).observe(this,mostPopularTVShowResponse ->{
            activityMainBinding.setIsLoading(false);
            if (mostPopularTVShowResponse != null){
                if (mostPopularTVShowResponse.getTvShows() != null){
                    tvShows.addAll(mostPopularTVShowResponse.getTvShows());
                    tvShowsAdapter.notifyDataSetChanged();
                }
            }
        });

    }
}*/
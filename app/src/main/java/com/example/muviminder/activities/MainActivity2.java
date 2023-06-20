/*package com.example.muviminder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.muviminder.R;
import com.example.muviminder.adapters.TVShowsAdapter;
import com.example.muviminder.databinding.ActivityMainBinding;
import com.example.muviminder.models.TvShow;
import com.example.muviminder.viewModel.MostPopularTVShowViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private MostPopularTVShowViewModel viewModel;
    private List<TvShow> tvShows = new ArrayList<>();
    private TVShowsAdapter tvShowsAdapter;
    private int currentPage =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }


    private void doInitialization(){
        activityMainBinding.tvShowRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(mostPopularTVShowsModel.class);
        tvShowsAdapter = new TVShowsAdapter(tvShows);
        activityMainBinding.tvShowsRecyclerView.setAdapter(tvShowsAdapter);
        activityMainBinding.tvShowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView , int dx,int dy){
                super.onScrolled(recyclerView,dx,dy);
                if (!activityMainBinding.tvShowsRecyclerView.canScrollVertically(1)){
                    if (currentPage <= totalAvailablePages){
                        currentPage +=1;
                        getMostPopularTVShows();
                    }
                }
            }
        });
    }

    private void getMostPopularTVShows(){
        activityMainBinding.setIsLoading(true);
        viewModel.getMostPopularTVShows(currentPage).observe(this,mostPopularTVShowResponse ->{
            activityMainBinding.setIsLoading(false);
            if (mostPopularTVShowResponse != null){
                if (mostPopularTVShowResponse.getTvShows() != null){
                    tvShows.addAll(mostPopularTVShowResponse.getTvShows());
                    tvShowsAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void toggleLoading(){
        if (currentPage==1){
            if (activityMainBinding.getIsLoading() !=null && activityMainBinding.getIsLoading()){
                activityMainBinding.setIsLoading(false);
            }else {
                activityMainBinding.setIsLoading(true);
            }
        }else {
            if (activityMainBinding.getIsLoadingMore() !=null && activityMainBinding.getIsLoadingMore()){
                activityMainBinding.setIsLoadingMore(false);
            }else {
                activityMainBinding.setIsLoadingMore(true);
            }
        }
    }
}*/
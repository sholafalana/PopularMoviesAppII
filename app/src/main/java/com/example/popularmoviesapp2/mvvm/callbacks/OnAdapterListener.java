package com.example.popularmoviesapp2.mvvm.callbacks;

import android.widget.ImageView;

import com.example.popularmoviesapp2.mvvm.datamodel.MovieData;


public interface OnAdapterListener {
    void getNextPagingData();

    void moveToDetailsScreen(ImageView imageView, MovieData movieData);
}

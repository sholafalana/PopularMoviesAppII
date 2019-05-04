package com.example.popularmoviesapp2.mvvm.callbacks;

import android.widget.ImageView;

import com.example.popularmoviesapp2.mvvm.datamodel.FavouriteMovieData;


public interface OnBookmarkAdapterListener {
    void moveToDetailsScreen(ImageView imageView, FavouriteMovieData favouriteMovieData);
}

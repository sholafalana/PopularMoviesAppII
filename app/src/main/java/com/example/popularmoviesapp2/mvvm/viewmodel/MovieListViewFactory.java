package com.example.popularmoviesapp2.mvvm.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.popularmoviesapp2.mvvm.datasource.AppDatabase;

/**
 * Created by USER on 5/3/2019.
 */

public class MovieListViewFactory extends ViewModelProvider.NewInstanceFactory {
    //TODO: create a viewmodel factory class for the MovieGridView activity
    private final AppDatabase appDatabase;
    private final String sortBy;

    public MovieListViewFactory(AppDatabase appDatabase, String sortBy) {
        this.appDatabase = appDatabase;
        this.sortBy = sortBy;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieGridViewModel(appDatabase, sortBy);
    }
}

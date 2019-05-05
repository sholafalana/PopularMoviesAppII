package com.example.popularmoviesapp2.mvvm.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.popularmoviesapp2.mvvm.datasource.AppDatabase;

/**
 * Created by USER on 5/3/2019.
 */

public class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppDatabase appDatabase;
    private final String id;

    //TODO: create a MovieViewModelFactory constructor taking in database and string as parameter
    public MovieViewModelFactory(AppDatabase appDatabase, String id) {
        this.appDatabase = appDatabase;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieViewModel(appDatabase, id);
    }
}

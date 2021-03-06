package com.example.popularmoviesapp2.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.popularmoviesapp2.mvvm.datamodel.FavouriteMovieData;
import com.example.popularmoviesapp2.mvvm.datasource.AppDatabase;

import java.util.List;

/**
 * Created by USER on 5/3/2019.
 */

public class FavouriteMovieViewModel extends AndroidViewModel {
    //TODO: create a viewmodel factory class for the FavouriteMovies activity
    private LiveData<List<FavouriteMovieData>> listLiveData;

    //TODO: create a viewmodel constructor with application as parameter
    public FavouriteMovieViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        listLiveData = appDatabase.favoriteDao().getFavList();
    }

    public LiveData<List<FavouriteMovieData>> getFavListLiveData() {
        return listLiveData;
    }
}

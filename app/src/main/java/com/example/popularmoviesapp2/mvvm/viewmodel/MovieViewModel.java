package com.example.popularmoviesapp2.mvvm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.popularmoviesapp2.mvvm.datamodel.MovieData;
import com.example.popularmoviesapp2.mvvm.datasource.AppDatabase;

/**
 * Created by USER on 5/3/2019.
 */

public class MovieViewModel extends ViewModel {
    //TODO: create a viewmodel class for the detailactivity
    private LiveData<MovieData> movieLiveData;
    private AppDatabase appDatabase;

    //TODO: create a MovieViewModel constructor taking in database and string as parameter
    public MovieViewModel(AppDatabase appDatabase, String id) {
        this.appDatabase = appDatabase;
        movieLiveData = appDatabase.movieDao().getMovie(id);
    }

    public LiveData<MovieData> getMovieLiveData() {
        return movieLiveData;
    }

    public void setMovieLiveData(MovieData movieData) {
        appDatabase.movieDao().updateMovie(movieData);
    }
}

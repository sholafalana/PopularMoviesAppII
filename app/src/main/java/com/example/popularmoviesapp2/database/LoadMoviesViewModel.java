package com.example.popularmoviesapp2.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import java.util.List;

/**
 * Created by shola on 9/7/2018.
 */

public class LoadMoviesViewModel extends AndroidViewModel {

    private AppDatabase database;

    private LiveData<List<MovieEntity> > movies;
    private LiveData<List<MovieEntity> > movie;

    public LoadMoviesViewModel(@NonNull Application application) {
        super(application);

        database = AppDatabase.getInstance(this.getApplication());

        movies = database.movieDataDao().getAllMovies();

    }

    public LiveData<List<MovieEntity>> getMovies() {
        return movies;
    }

    public LiveData<List<MovieEntity>> getMovie(int mMovieId){
        movie = database.movieDataDao().loadMovieById(mMovieId);
        return movie;
    }


}
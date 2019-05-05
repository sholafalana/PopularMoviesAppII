package com.example.popularmoviesapp2.mvvm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


import com.example.popularmoviesapp2.mvvm.datamodel.MovieData;
import com.example.popularmoviesapp2.mvvm.datasource.AppDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 5/3/2019.
 */

public class MovieGridViewModel extends ViewModel {
    //TODO: create a viewmodel class for the MovieGridViewModel activity
    private MutableLiveData<List<MovieData>> moveList = new MutableLiveData<>();

    public MovieGridViewModel(AppDatabase appDatabase, String sortBy) {
        moveList.postValue(appDatabase.movieDao().getMovieTaskList(sortBy));
    }

    public LiveData<List<MovieData>> getMovieLiveData() {
        return moveList;
    }

    public void clear() {
        moveList.setValue(new ArrayList<MovieData>());
    }


    public void setMovieLiveList(List<MovieData> data) {
        List<MovieData> list = moveList.getValue();
        list.addAll(data);
        moveList.setValue(list);
    }

    public void setNewMovieList(List<MovieData> data) {
        moveList.setValue(data);
    }
}

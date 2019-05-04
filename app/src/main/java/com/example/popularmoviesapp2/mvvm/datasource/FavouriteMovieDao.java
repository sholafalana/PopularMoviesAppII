package com.example.popularmoviesapp2.mvvm.datasource;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.popularmoviesapp2.mvvm.datamodel.FavouriteMovieData;

import java.util.List;


@Dao
public interface FavouriteMovieDao {
    @Query("SELECT * FROM favourite_movie")
    LiveData<List<FavouriteMovieData>> getFavList();

    @Query("SELECT * FROM favourite_movie where id=:id")
    FavouriteMovieData checkFav(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavouriteMovieData favouriteMovieData);

    @Query("DELETE FROM favourite_movie where id=:id")
    void delete(String id);
}

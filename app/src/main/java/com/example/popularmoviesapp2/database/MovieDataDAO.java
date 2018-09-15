package com.example.popularmoviesapp2.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by shola on 9/7/2018.
 */

@Dao
public interface MovieDataDAO {

    @Query("SELECT * FROM movies ORDER BY mMovieId")
    LiveData<List<MovieEntity>> getAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieEntity movieEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(MovieEntity movieEntity);

    @Delete
    void deleteMovie(MovieEntity movieEntity);


    @Query("SELECT * FROM movies WHERE mMovieId = :movieId")
    LiveData<List<MovieEntity>> loadMovieById(int movieId);

    @Query("DELETE FROM movies WHERE mMovieId = :movieId")
    void deleteMovieById(int movieId);
}


package com.example.popularmoviesapp2.mvvm.datasource;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.popularmoviesapp2.mvvm.datamodel.FavouriteMovieData;
import com.example.popularmoviesapp2.mvvm.datamodel.MovieData;


@Database(entities = {MovieData.class, FavouriteMovieData.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "moviedb";
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext()
                        , AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .allowMainThreadQueries().build();
            }
        }
        return instance;
    }

    public abstract MovieDao movieDao();

    public abstract FavouriteMovieDao favoriteDao();
}

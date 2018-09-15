package com.example.popularmoviesapp2.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by shola on 9/8/2018.
 */
@Entity(tableName = "movies")
public class MovieEntity {
    int id;
    @PrimaryKey   @NonNull
    private int mMovieId;
    private String mTitle;
    private String mPosterImageURL;
    private String mPlotSynopsis;
    private String mRating;
    private String mReleaseDate;

    //Model for Movie in List
    @Ignore
    public MovieEntity() {

    }


    //Model for Movie Detail
    public MovieEntity(int movieId, String title, String posterImageURL, String plotSynopsis, String rating, String releaseDate) {
        mMovieId = movieId;
        mTitle = title;
        mPosterImageURL = posterImageURL;
        mPlotSynopsis = plotSynopsis;
        mRating = rating;
        mReleaseDate = releaseDate;


    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getMovieId() {
        return mMovieId;
    }

    public void setMovieId(int movieId) {
        mMovieId = movieId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPosterImageURL() {
        return mPosterImageURL;
    }

    public void setPosterImageURL(String posterImageURL) {
        mPosterImageURL = posterImageURL;
    }

    public String getPlotSynopsis() {
        return mPlotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        mPlotSynopsis = plotSynopsis;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        mRating = rating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

}

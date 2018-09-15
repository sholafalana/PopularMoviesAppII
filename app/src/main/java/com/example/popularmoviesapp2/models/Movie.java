package com.example.popularmoviesapp2.models;



public class Movie {

    private int mMovieId;
    private String mTitle;
    private String mPosterImageURL;
    private String mPlotSynopsis;
    private String mRating;
    private String mReleaseDate;

    //Model for Movie in List

    public Movie() {

    }


    //Model for Movie Detail
    public Movie(int movieId, String title, String posterImageURL, String plotSynopsis, String rating, String releaseDate) {
        mMovieId = movieId;
        mTitle = title;
        mPosterImageURL = posterImageURL;
        mPlotSynopsis = plotSynopsis;
        mRating = rating;
        mReleaseDate = releaseDate;


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

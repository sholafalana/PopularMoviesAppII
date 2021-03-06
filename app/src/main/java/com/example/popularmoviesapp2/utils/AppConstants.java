package com.example.popularmoviesapp2.utils;

import com.example.popularmoviesapp2.BuildConfig;


public class AppConstants {
    //TODO: declare constant variables and shared pref
    // API urls
    public static final String API_MOVIE_POPULAR_LIST = BuildConfig.BASE_URL + "movie/popular";
    public static final String API_MOVIE_HIGH_RATE_LIST = BuildConfig.BASE_URL + "movie/top_rated";
    public static final String API_MOVIE_DETAILS = BuildConfig.BASE_URL + "movie";

    // Intent keys
    public static final String EXTRA_MOVIE_DATA = "movieData";
    public static final String EXTRA_VIDEO_LIST = "videoList";
    public static final String EXTRA_IS_BOOKMARKED = "isBookMarked";

    /*Constants*/
    public static final int TRUE = 1;
    public static final int FALSE = 0;


    /* SharedPreference*/
    public static final String SHAREDPREF_NAME = "appData";
    public static final String SHAREDPREF_KEY_SORT_KEY = "sortBy";
    public static final String SHAREDPREF_KEY_SORT_URL = "mainUrl";
    public static final String SHAREDPREF_KEY_PAGE = "page";
    public static final String SHAREDPREF_VALUE_POPULAR = "popular";
    public static final String SHAREDPREF_VALUE_RATING = "rating";

}

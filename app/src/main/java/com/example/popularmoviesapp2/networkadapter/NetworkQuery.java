package com.example.popularmoviesapp2.networkadapter;

import com.example.popularmoviesapp2.BuildConfig;

public class NetworkQuery{

    private static final String movieAPIKey = BuildConfig.ApiKey;


    public String defaultQuery() {
        String movieURL;
        movieURL = "https://api.themoviedb.org/3/discover/movie?api_key="+ movieAPIKey;
        return movieURL;
    }

    public String popularMoviesQuery(){
        String movieURL;
        movieURL = "https://api.themoviedb.org/3/movie/popular?api_key="+ movieAPIKey;
        return movieURL;
    }

    public String topRatedMoviesQuery() {
        String movieURL;
        movieURL =  "https://api.themoviedb.org/3/movie/top_rated?api_key="+ movieAPIKey;
        return movieURL;
    }


}


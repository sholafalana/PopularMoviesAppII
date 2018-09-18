package com.example.popularmoviesapp2.models;


/**
 * Created by shola on 9/3/2018.
 */

public class ParseTrailer {
    private String mName;
    private String mKey;


    //Model for Movie in List
    public ParseTrailer() {

    }


    //Model for Movie Detail
    public ParseTrailer(String name, String key) {
        mName = name;
        mKey = key;


    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }


}
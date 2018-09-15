package com.example.popularmoviesapp2.models;

/**
 * Created by shola on 9/9/2018.
 */

public class ParseReview {
    private String mName;
    private String mContent;


    //Model for Movie in List
    public ParseReview() {

    }


    //Model for Movie Detail
    public ParseReview(String name, String content ) {
        mName = name;
        mContent = content;



    }
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }


}
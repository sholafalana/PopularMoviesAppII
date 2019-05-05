package com.example.popularmoviesapp2.mvvm.datasource;

import android.arch.persistence.room.TypeConverter;

import com.example.popularmoviesapp2.mvvm.datamodel.ReviewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class ReviewConvertor {
    //TODO: create a TypeConverter class for review model

    @TypeConverter
    public static String encodeGenre(List<ReviewModel> reviewModelList) {

        Gson gson = new Gson();
        return gson.toJson(reviewModelList);
    }

    @TypeConverter
    public static List<ReviewModel> decodeGenre(String value) {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<ReviewModel>>() {
        }.getType();
        return gson.fromJson(value, listType);
    }
}

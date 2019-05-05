package com.example.popularmoviesapp2.mvvm.datasource;

import android.arch.persistence.room.TypeConverter;

import com.example.popularmoviesapp2.mvvm.datamodel.CastModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CastsConvertor {
    //TODO: create a TypeConverter class for cast model
    @TypeConverter
    public static String encodeGenre(List<CastModel> genreModelList) {

        Gson gson = new Gson();
        return gson.toJson(genreModelList);
    }

    @TypeConverter
    public static List<CastModel> decodeGenre(String value) {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<CastModel>>() {
        }.getType();
        return gson.fromJson(value, listType);
    }
}

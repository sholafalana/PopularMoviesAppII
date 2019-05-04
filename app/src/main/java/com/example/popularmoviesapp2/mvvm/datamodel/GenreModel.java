package com.example.popularmoviesapp2.mvvm.datamodel;

import android.arch.persistence.room.ColumnInfo;


public class GenreModel {
    @ColumnInfo(name = "gid")
    private String id;
    @ColumnInfo(name = "gname")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

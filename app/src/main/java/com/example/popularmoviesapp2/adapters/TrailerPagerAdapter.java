package com.example.popularmoviesapp2.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.popularmoviesapp2.mvvm.View.TrailerFragment;

import java.util.List;


public class TrailerPagerAdapter extends FragmentPagerAdapter {
    //TODO: create a view adapter for the trailer video list
    private List<String> list;

    public TrailerPagerAdapter(FragmentManager fm, List<String> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return TrailerFragment.newInstance(list.get(position));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

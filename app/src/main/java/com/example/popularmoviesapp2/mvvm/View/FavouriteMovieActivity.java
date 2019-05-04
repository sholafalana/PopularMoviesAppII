package com.example.popularmoviesapp2.mvvm.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.popularmoviesapp2.R;
import com.example.popularmoviesapp2.adapters.FavouriteMovieAdapter;
import com.example.popularmoviesapp2.mvvm.callbacks.OnBookmarkAdapterListener;
import com.example.popularmoviesapp2.mvvm.datamodel.FavouriteMovieData;
import com.example.popularmoviesapp2.utils.AppConstants;
import com.example.popularmoviesapp2.mvvm.viewmodel.FavouriteMovieViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteMovieActivity extends AppCompatActivity implements OnBookmarkAdapterListener {

    @BindView(R.id.ryc_movie_list)
    RecyclerView rycMovieList;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_list);

        ButterKnife.bind(this);
        context = this;
        getSupportActionBar().setTitle(getString(R.string.bookmarks));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        rycMovieList.setLayoutManager(gridLayoutManager);
        setUpViewModel();
    }

    private void setUpViewModel() {
        FavouriteMovieViewModel bookmarkListViewModel = ViewModelProviders.of((FragmentActivity) context).get(FavouriteMovieViewModel.class);
        bookmarkListViewModel.getFavListLiveData().observe(FavouriteMovieActivity.this, new Observer<List<FavouriteMovieData>>() {
            @Override
            public void onChanged(@Nullable List<FavouriteMovieData> movieDataList) {
                FavouriteMovieAdapter favouriteMovieAdapter = new FavouriteMovieAdapter(context, new ArrayList<>(movieDataList));
                rycMovieList.setAdapter(favouriteMovieAdapter);
                if (movieDataList.size() == 0)
                    Toast.makeText(context, getString(R.string.noDataFound), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void moveToDetailsScreen(ImageView imageView, FavouriteMovieData favouriteMovieData) {

        Intent intent = new Intent(FavouriteMovieActivity.this, DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EXTRA_MOVIE_DATA, favouriteMovieData.getId());
        intent.putExtras(bundle);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(FavouriteMovieActivity.this,
                        imageView,
                        getString(R.string.poster_transition_name));
        startActivity(intent, options.toBundle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_to_right);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_to_right);
    }
}

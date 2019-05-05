package com.example.popularmoviesapp2.mvvm.View;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.popularmoviesapp2.BuildConfig;
import com.example.popularmoviesapp2.R;
import com.example.popularmoviesapp2.adapters.MovieGridAdapter;
import com.example.popularmoviesapp2.mvvm.callbacks.OnAdapterListener;
import com.example.popularmoviesapp2.mvvm.datamodel.MovieData;
import com.example.popularmoviesapp2.mvvm.datasource.AppDatabase;
import com.example.popularmoviesapp2.utils.AppConstants;
import com.example.popularmoviesapp2.utils.App;
import com.example.popularmoviesapp2.utils.Util;
import com.example.popularmoviesapp2.mvvm.viewmodel.MovieListViewFactory;
import com.example.popularmoviesapp2.mvvm.viewmodel.MovieGridViewModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesGridActivity extends AppCompatActivity implements OnAdapterListener {

    @BindView(R.id.ryc_movie_list)
    RecyclerView rycMovieList;

    private Context context;
    private App app;
    private int paging = 1;
    private ArrayList<MovieData> movieList;
    private MovieGridAdapter movieGridAdapter;
    private ProgressDialog progressDialog;
    private String mainURL;
    private MovieGridViewModel viewModel;
    private String sortby = AppConstants.SHAREDPREF_VALUE_POPULAR;
    private AppDatabase appDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        ButterKnife.bind(this);
        context = this;
        app = App.getAppInstance();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);

        int spanCount = 3;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 4;
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanCount);
        rycMovieList.setLayoutManager(gridLayoutManager);

        movieList = new ArrayList<>();
        mainURL = app.getSortAPI();
        sortby = app.getSortBy();
        paging = app.getPaging();
        appDatabase = AppDatabase.getInstance(context);

      /*  rycMovieList.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int lastVisibleItemPosition = ((LinearLayoutManager) rycMovieList.getLayoutManager())
                        .findLastCompletelyVisibleItemPosition();
                if (lastVisibleItemPosition == movieGridAdapter.getItemCount() - 1) {

                }
            }
        });   */
        //   ++paging;
        // getMovieList();
        setUpViewModel();
    }

    //TODO: get list of latest movies
    private void getMovieList() {

        progressDialog.show();
        Uri.Builder params = new Uri.Builder();
        params.appendQueryParameter(getString(R.string.key_api_key), BuildConfig.API_KEY);
        params.appendQueryParameter(getString(R.string.key_page_no), String.valueOf(paging));

        StringRequest stringRequest = new StringRequest(Request.Method.GET, mainURL + params, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray(getString(R.string.key_results));
                    if (dataArray.length() > 0) {
                        if (paging == 1)
                            appDatabase.movieDao().deleteAll(sortby);

                        Gson gson = new Gson();
                        ArrayList<MovieData> list = new ArrayList<>();
                        for (int i = 0; i < dataArray.length(); i++) {
                            MovieData movieData = gson.fromJson(dataArray.getJSONObject(i).toString(), MovieData.class);
                            movieData.setSortby(sortby);
                            list.add(movieData);
                        }
                        appDatabase.movieDao().insertAll(list);
                        viewModel.setMovieLiveList(list);

                    } else
                        Toast.makeText(context, getString(R.string.somethingGoneWrong), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    reset();
                    progressDialog.dismiss();
                    Toast.makeText(context, getString(R.string.somethingGoneWrong), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                reset();
                progressDialog.dismiss();
                Util.showError(error, context);
            }
        });
        app.addToRequestQueue(stringRequest);
    }

    private void setUpViewModel() {
        MovieListViewFactory factory = new MovieListViewFactory(appDatabase, sortby);
        viewModel = ViewModelProviders.of((FragmentActivity) context, factory).get(MovieGridViewModel.class);
        viewModel.getMovieLiveData().observe(MoviesGridActivity.this, observer);
    }

    Observer<List<MovieData>> observer = new Observer<List<MovieData>>() {
        @Override
        public void onChanged(@Nullable List<MovieData> movieDataList) {
            populateList(movieDataList);
        }
    };

    private void populateList(@NonNull List<MovieData> movieDataList) {
        if (movieDataList.size() != 0) {
            movieList.addAll(movieDataList.subList(movieList.size(), movieDataList.size() - 1));
            if (movieGridAdapter == null) {
                movieGridAdapter = new MovieGridAdapter(context, movieList);
                rycMovieList.setAdapter(movieGridAdapter);

            } else
                movieGridAdapter.notifyDataSetChanged();
            app.savePaging(paging);
        } else if (Util.checkConnection(context)) {

            getMovieList();
        } else
            Util.showError(new NetworkError(), context);
    }

    private void reset() {
        if (paging == 0)
            paging = 1;
        else
            --paging;
    }


    @Override
    public synchronized void getNextPagingData() {

    }

    //TODO: intent to detail activity
    @Override
    public void moveToDetailsScreen(ImageView imageView, MovieData movieData) {
        Intent intent = new Intent(MoviesGridActivity.this, DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EXTRA_MOVIE_DATA, movieData.getId());
        intent.putExtras(bundle);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(MoviesGridActivity.this,
                        imageView,
                        getString(R.string.poster_transition_name));
        startActivity(intent, options.toBundle());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        movieList = new ArrayList<>();
        if (id == R.id.most_popular) {
            mainURL = AppConstants.API_MOVIE_POPULAR_LIST;
            paging = 1;
            app.saveSortAPI(mainURL);
            sortby = AppConstants.SHAREDPREF_VALUE_POPULAR;
            app.saveSortBy(sortby);
            movieList = new ArrayList<>();
            movieGridAdapter = null;
            if (Util.checkConnection(context)) {
                viewModel.clear();
            } else {
                viewModel.setNewMovieList(appDatabase.movieDao().getMovieTaskList(sortby));
            }

        } else if (id == R.id.high_rate) {
            mainURL = AppConstants.API_MOVIE_HIGH_RATE_LIST;
            app.saveSortAPI(mainURL);
            sortby = AppConstants.SHAREDPREF_VALUE_RATING;
            app.saveSortBy(sortby);
            paging = 1;
            movieList = new ArrayList<>();
            movieGridAdapter = null;
            if (Util.checkConnection(context)) {
                viewModel.clear();
            } else {
                viewModel.setNewMovieList(appDatabase.movieDao().getMovieTaskList(sortby));
            }

        } else if (id == R.id.favorites) {
            Util.finishEntryAnimation(context, new Intent(context, FavouriteMovieActivity.class));
        }
        return true;
    }
}

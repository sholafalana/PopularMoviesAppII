package com.example.popularmoviesapp2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.popularmoviesapp2.database.LoadMoviesViewModel;
import com.example.popularmoviesapp2.database.MovieEntity;
import com.example.popularmoviesapp2.models.Movie;
import com.example.popularmoviesapp2.networkadapter.NetworkSingleton;
import com.example.popularmoviesapp2.viewadapter.MovieAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener {
    //Instantiate Object need display content for moviesAPI
    private static final String TAG = MainActivity.class.getSimpleName();
    private MovieAdapter mMovieAdapter;
    private ArrayList<Movie> mMovieList;
    private final Context context = this;
    private static final String movieAPIKey = BuildConfig.ApiKey;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    // Movie URL
    public static final String baseImageURL = "http://image.tmdb.org/t/p/w185";

    //Instantiate Values as keys to use for Movie Detail
    public static final String EXTRA_MOVIE_ID = "movieId";
    public static final String EXTRA_MOVIE_SUMMARY = "movieSummary";
    public static final String EXTRA_MOVIE_RELEASE_DATE = "releaseDate";
    public static final String EXTRA_MOVIE_RATING = "movieRating";
    public static final String EXTRA_MOVIE_IMAGE_URL = "imageUrl";
    public static final String EXTRA_MOVIE_TITLE = "movieTitle";


    //Declare Request Queue needed to make calls with volley
    private RequestQueue mRequestQueue;


    //Network Query Constructor
    public String defaultMovieURL = "https://api.themoviedb.org/3/discover/movie?api_key=" + movieAPIKey;
    public String popularMovieURL = "https://api.themoviedb.org/3/movie/popular?api_key=" + movieAPIKey;
    public String highestRatedURL = "https://api.themoviedb.org/3/movie/top_rated?api_key=" + movieAPIKey;

    /* Recycler view set up */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        GridLayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        } else {
            layoutManager = new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false);
        }
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);

        mMovieList = new ArrayList<>();
        mRequestQueue = NetworkSingleton.getInstance(this).getRequestQueue();

      /*  if (!amIOnline()){
            fetcFavouriteMovies();
        }
        else {
            fetchMovies(defaultMovieURL);
        }   */

        fetchMovies(defaultMovieURL);

    }

    // Menu Item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sorting_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.most_popular:
                fetchMovies(popularMovieURL);
                return true;
            case R.id.highest_rating:
                fetchMovies(highestRatedURL);
                return true;
            case R.id.favourite_movies:
                fetcFavouriteMovies();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fetchMovies(String requestURL) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestURL, null, onMoviesLoaded, onMoviesError);
        mRequestQueue.add(request);

    }

    private final Response.Listener<JSONObject> onMoviesLoaded = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            //Parse results
            try {
                //Clear current movies List Before parsing
                mMovieList.clear();
                JSONArray resultsArray = response.getJSONArray("results");
                //Loop through results in array for populating the UI
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject movieResult = resultsArray.getJSONObject(i);

                    Movie movie = new Movie();

                    //Parse results to Movie Object
                    movie.setMovieId(Integer.valueOf (movieResult.getString("id")));
                    movie.setTitle(movieResult.getString("title"));
                    movie.setPosterImageURL(baseImageURL.concat((movieResult.getString("poster_path"))));
                    movie.setReleaseDate(formatDate(movieResult.getString("release_date")));
                    movie.setRating(String.valueOf(movieResult.getDouble("vote_average")));
                    movie.setPlotSynopsis(movieResult.getString("overview"));

                    //Add values to movieArrayList
                    mMovieList.add(movie);
                }

                //Bind Movie Date using an Adapter
                mMovieAdapter = new MovieAdapter(MainActivity.this, mMovieList);
                mMovieAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mMovieAdapter);
                mMovieAdapter.setOnItemClickListener(MainActivity.this);
                Log.d(TAG, "onResponse: ");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    private final Response.ErrorListener onMoviesError = error -> error.printStackTrace();

    // Intent to detailed activity
    @Override
    public void onItemClick(int position) {
        Intent movieDetailIntent = new Intent(context, DetailActivity.class);
        Movie selectedMovie = mMovieList.get(position);

        movieDetailIntent.putExtra(EXTRA_MOVIE_ID,String.valueOf (selectedMovie.getMovieId()));
        movieDetailIntent.putExtra(EXTRA_MOVIE_TITLE, selectedMovie.getTitle());
        movieDetailIntent.putExtra(EXTRA_MOVIE_IMAGE_URL, selectedMovie.getPosterImageURL());
        movieDetailIntent.putExtra(EXTRA_MOVIE_SUMMARY, selectedMovie.getPlotSynopsis());
        movieDetailIntent.putExtra(EXTRA_MOVIE_RATING, selectedMovie.getRating());
        movieDetailIntent.putExtra(EXTRA_MOVIE_RELEASE_DATE, selectedMovie.getReleaseDate());



        context.startActivity(movieDetailIntent);

    }


     String formatDate(String date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        String formattedDate;

        try {
            Date currentDate = dateFormatter.parse(date);
            dateFormatter.applyPattern("MMMM dd, yyyy");
            formattedDate = dateFormatter.format(currentDate);

        } catch (ParseException e) {
            formattedDate = "";
            e.printStackTrace();
        }


        return formattedDate;
    }

    private boolean amIOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = Objects.requireNonNull(cm).getActiveNetworkInfo();
        return nInfo != null && nInfo.isConnectedOrConnecting();
    }


    private void fetcFavouriteMovies() {

        mMovieList.clear();
        LoadMoviesViewModel favMoviesViewModel = ViewModelProviders.of(this).get(LoadMoviesViewModel.class);
        favMoviesViewModel .getMovies().observe(this, new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(@Nullable List<MovieEntity> movieEntities) {

                for (MovieEntity movieEntity :movieEntities) {

                    Movie movies = new Movie(movieEntity.getMovieId(),movieEntity.getTitle(),
                            movieEntity.getPosterImageURL(), movieEntity.getPlotSynopsis(), movieEntity.getRating(),
                            movieEntity.getReleaseDate());
                    mMovieList.add(movies);
                }

                mMovieAdapter = new MovieAdapter(MainActivity.this, mMovieList);
                mRecyclerView.setAdapter(mMovieAdapter);
                mMovieAdapter.setOnItemClickListener(MainActivity.this);

                mMovieAdapter.notifyDataSetChanged();


            }
        });

    }


}




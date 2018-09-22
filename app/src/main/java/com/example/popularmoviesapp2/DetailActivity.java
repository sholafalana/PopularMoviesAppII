package com.example.popularmoviesapp2;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.popularmoviesapp2.database.FavMoviesVModel;
import com.example.popularmoviesapp2.database.LoadMoviesViewModel;
import com.example.popularmoviesapp2.database.MovieEntity;
import com.example.popularmoviesapp2.models.ParseTrailer;
import com.example.popularmoviesapp2.networkadapter.NetworkSingleton;
import com.example.popularmoviesapp2.viewadapter.MovieTrailerAdapter;
import com.squareup.picasso.Picasso;
import static com.example.popularmoviesapp2.MainActivity.EXTRA_MOVIE_ID;
import static com.example.popularmoviesapp2.MainActivity.EXTRA_MOVIE_IMAGE_URL;
import static com.example.popularmoviesapp2.MainActivity.EXTRA_MOVIE_RATING;
import static com.example.popularmoviesapp2.MainActivity.EXTRA_MOVIE_RELEASE_DATE;
import static com.example.popularmoviesapp2.MainActivity.EXTRA_MOVIE_SUMMARY;
import static com.example.popularmoviesapp2.MainActivity.EXTRA_MOVIE_TITLE;
import static com.example.popularmoviesapp2.R.color.red_button;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.android.volley.Request.Method.GET;


public class DetailActivity extends AppCompatActivity implements MovieTrailerAdapter.OnItemClickListener  {

    String BASE_URL = "https://api.themoviedb.org/3/movie/";
    String TRAILER_BUILD = "/videos?api_key=";
    String FULL_VIDEO_URL = "";
    String YouTubeUrl = "https://www.youtube.com/watch?v=";
    String ShareTrailerKEY = "";
    String movieID;
    private final String ADDED_TAG = "favourited";
    private final String REMOVED_TAG = "notfavourited";
    private final Context context = this;
    private static final String movieAPIKey = BuildConfig.ApiKey;
    private RequestQueue mRequestQueue;
    MovieTrailerAdapter mTrailersAdapter;
    List<ParseTrailer> mTrailerList;
    private static int MOVIE_ID ;
    private static  String MOVIE_SUMMARY ;
    private static  String MOVIE_RELEASE_DATE ;
    private static  String MOVIE_RATING ;
    private static  String MOVIE_IMAGE_URL ;
    private static  String MOVIE_TITLE ;

    @BindView(R.id.detail_movie_title)
    TextView title_tv;
    @BindView(R.id.release_date)
    TextView releaseDate_tv;
    @BindView(R.id.plot_summary)
    TextView plot_tv;
    @BindView(R.id.user_rating)
    TextView vote_iv;
    @BindView(R.id.detail_poster_image)
    ImageView poster_iv;
    @BindView(R.id.trailer_r_view)
    RecyclerView trailerRecyclerView;
    @BindView(R.id.reviews)
    Button review;
    @BindView(R.id.floatingFavButton)
    FloatingActionButton floatingFavButton;
    @BindView(R.id.movieTrailers)
    TextView movieTrailer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        trailerRecyclerView.setLayoutManager(layoutManager);
        trailerRecyclerView.setHasFixedSize(true);
        mTrailerList = new ArrayList<>();


        Intent mainMovieIntent = getIntent();
        if (mainMovieIntent != null) {
            movieID = mainMovieIntent.getStringExtra(EXTRA_MOVIE_ID);


            String movieTitle = mainMovieIntent.getStringExtra(EXTRA_MOVIE_TITLE);
            String movieImageUrl = mainMovieIntent.getStringExtra(EXTRA_MOVIE_IMAGE_URL);
            String moviePlotSummary = mainMovieIntent.getStringExtra(EXTRA_MOVIE_SUMMARY);
            String movieRating = mainMovieIntent.getStringExtra(EXTRA_MOVIE_RATING);
            String movieReleaseDate = mainMovieIntent.getStringExtra(EXTRA_MOVIE_RELEASE_DATE);

           MOVIE_ID = Integer.valueOf(movieID) ;

            checkIfMovieExistsInFavorite(MOVIE_ID);

            review.setOnClickListener(v -> {
                Intent intent1 = new Intent(context, ReviewActivity.class);
                intent1.putExtra(getResources().getString(R.string.KEY_MOVIE_ID), movieID);
                context.startActivity(intent1);

            });

             MOVIE_SUMMARY = moviePlotSummary ;
             MOVIE_RELEASE_DATE = movieReleaseDate;
             MOVIE_RATING = movieRating ;
             MOVIE_IMAGE_URL = movieImageUrl ;
             MOVIE_TITLE = movieTitle ;

            String Api_Build = TRAILER_BUILD + movieAPIKey ;
            FULL_VIDEO_URL = BASE_URL + movieID + Api_Build;
            fetchTrailer(FULL_VIDEO_URL);

            setTitle(null);
            title_tv.setText(movieTitle);
            Picasso.get().load(movieImageUrl).fit().centerCrop().into(poster_iv);
            plot_tv.setText(moviePlotSummary);
            vote_iv.setText(movieRating);
            releaseDate_tv.setText(movieReleaseDate);

           floatingFavButton.setOnClickListener(v -> {

                FloatingActionButton fab = (FloatingActionButton) v;
                String fabTag = (String) fab.getTag();

                if (fabTag.equals(REMOVED_TAG)) {
                    floatingFavButton.setImageResource(R.drawable.btn_star_big_on);
                    fab.setTag(ADDED_TAG);
                    addMovieToFavorites();
                    Toast.makeText(this,
                            getResources().getText(R.string.favourite_added),
                            Toast.LENGTH_SHORT).show();
                } else if (fabTag.equals(ADDED_TAG)) {
                    floatingFavButton.setImageResource(R.drawable.btn_star_big_off);
                    fab.setTag(REMOVED_TAG);
                    deleteMovieFromFavorites();
                    Toast.makeText(this,
                            getResources().getText(R.string.favourite_removed),
                            Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void fetchTrailer(String url){

        StringRequest stringRequest = new StringRequest(GET, url, response -> {

            try {
                mTrailerList.clear();
                JSONObject jsonObject = new JSONObject(response);
                JSONArray results = jsonObject.optJSONArray(getResources().getString(R.string.KEY_RESULTS));
                for (int i = 0; i < results.length(); i++){
                    if(i == 0){
                        JSONObject jo = results.optJSONObject(i);
                        ParseTrailer trailer = new ParseTrailer();
                        trailer.setKey(jo.getString("key"));
                        ShareTrailerKEY = trailer.getKey();
                    }
                    JSONObject jo = results.optJSONObject(i);
                    ParseTrailer trailer = new ParseTrailer();
                    trailer.setName(jo.optString("name"));
                    trailer.setKey(jo.optString("key"));
                    mTrailerList.add(trailer);
                }
                if(mTrailerList.isEmpty()){
                    movieTrailer.setTextColor(getResources().getColor(red_button));
                    movieTrailer.setText(getResources().getText(R.string.no_trailers_error));
                }else {
                    movieTrailer.setText(getResources().getText(R.string.movie_trailer_tv));
                    mTrailersAdapter = new  MovieTrailerAdapter(mTrailerList, getApplicationContext(), this);
                    trailerRecyclerView.setAdapter(mTrailersAdapter);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this,
                getResources().getText(R.string.no_connectivity_error),
                Toast.LENGTH_LONG).show()
        );
        mRequestQueue = NetworkSingleton.getInstance(this).getRequestQueue();
        mRequestQueue.add(stringRequest);
    }



    @Override
    public void onItemClick(int clickedItemIndex) {

        ParseTrailer trailer1 = mTrailerList.get(clickedItemIndex);
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer1.getKey()));
        startActivity(appIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.share_button && ShareTrailerKEY.length() > 0) {
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType("text/plain")
                    .setChooserTitle(getResources().getText(R.string.share_trailer))
                    .setText(getResources().getText(R.string.recommedation) + YouTubeUrl + ShareTrailerKEY)
                    .startChooser();
        } else {
            item.setVisible(false);
        }
        return super.onOptionsItemSelected(item);
    }

     // check if movie exist in favourite collections
    private void checkIfMovieExistsInFavorite(int movieId) {


        LoadMoviesViewModel viewModel = ViewModelProviders.of(this).get(LoadMoviesViewModel.class);
        viewModel.getMovie(movieId).observe(this, MovieEntity -> {
            if(MovieEntity.size() <= 0){
                floatingFavButton.setTag(REMOVED_TAG);
                floatingFavButton.setImageResource(R.drawable.btn_star_big_off);
            }else {

                floatingFavButton.setTag(ADDED_TAG);
                floatingFavButton.setImageResource(R.drawable.btn_star_big_on);
            }

        });


    }

    // add movie to favourite
   private void addMovieToFavorites(){
        MovieEntity movieEntity = new MovieEntity(MOVIE_ID,MOVIE_TITLE,MOVIE_IMAGE_URL,MOVIE_SUMMARY,
              MOVIE_RATING,MOVIE_RELEASE_DATE);
        FavMoviesVModel addFavouriteMovieViewModel = ViewModelProviders.of(this).get(FavMoviesVModel.class);
      addFavouriteMovieViewModel.insertItem(movieEntity);
  }

    // delete movie from favourite
  private void deleteMovieFromFavorites(){
      FavMoviesVModel addFavouriteMovieViewModel = ViewModelProviders.of(this).get(FavMoviesVModel.class);
      addFavouriteMovieViewModel.deleteItem(MOVIE_ID);

  }



}

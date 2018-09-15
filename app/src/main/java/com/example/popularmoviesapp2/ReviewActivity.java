package com.example.popularmoviesapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.popularmoviesapp2.models.ParseReview;
import com.example.popularmoviesapp2.networkadapter.NetworkSingleton;
import com.example.popularmoviesapp2.viewadapter.MovieReviewAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewActivity extends AppCompatActivity {


    private static final String movieAPIKey = BuildConfig.ApiKey;
    String API_BUILD = "/reviews?api_key="+movieAPIKey;
    String REVIEW_BASE_URL = "https://api.themoviedb.org/3/movie/";
    String FULL_REVIEW_URL = "";
    ArrayList<ParseReview> reviewList;
    MovieReviewAdapter movieReviewsAdapter;
    @BindView(R.id.noReview)
    TextView noReviews;
    @BindView(R.id.reviewRecyclerView)
    RecyclerView reviewRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        setTitle("User Reviews");
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviewRecyclerView.setLayoutManager(layoutManager);
        reviewList = new ArrayList<>();




        Intent reviewIntent = getIntent();
        if (reviewIntent != null){
            String movieID = reviewIntent.getStringExtra(getResources().getString(R.string.KEY_MOVIE_ID));
            FULL_REVIEW_URL = REVIEW_BASE_URL + movieID + API_BUILD;
           // loadReviewData
            fetchMoviesReview (FULL_REVIEW_URL);
        }

    }


    private void fetchMoviesReview(String requestURL) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestURL, null, onMoviesLoaded, onMoviesError);
        NetworkSingleton.getInstance(this).getRequestQueue().add(request);

    }

    private final Response.Listener<JSONObject> onMoviesLoaded = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            //Parse results
            try {
                //Clear current movies List Before parsing
                reviewList.clear();
                JSONArray resultsArray = response.getJSONArray("results");
                //Loop through results in array for populating the UI
                for (int i = 0; i < resultsArray.length(); i++){
                    JSONObject jo = resultsArray.optJSONObject(i);
                    ParseReview review = new ParseReview(jo.optString(getResources().getString(R.string.KEY_REVIEW_AUTHOR)),jo.optString(getResources().getString(R.string.KEY_REVIEW_CONTENT)));
                    reviewList.add(review);
                }

                if(reviewList.isEmpty()){
                    noReviews.setText(getResources().getText(R.string.no_reviews_error));
                    noReviews.setVisibility(View.VISIBLE);
                }else {
                    movieReviewsAdapter = new MovieReviewAdapter(reviewList, getApplicationContext());
                    reviewRecyclerView.setAdapter(movieReviewsAdapter);
                    noReviews.setVisibility(View.INVISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    private final Response.ErrorListener onMoviesError = error -> error.printStackTrace();

}

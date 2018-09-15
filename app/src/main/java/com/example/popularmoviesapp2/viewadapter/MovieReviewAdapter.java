package com.example.popularmoviesapp2.viewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.popularmoviesapp2.R;
import com.example.popularmoviesapp2.models.ParseReview;

import java.util.ArrayList;

/**
 * Created by shola on 9/9/2018.
 */

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ViewHolder> {

private final ArrayList<ParseReview> reviews;
private final Context context;


public MovieReviewAdapter(ArrayList<ParseReview> reviews, Context context) {

        this.reviews = reviews;
        this.context = context;
        }

@NonNull
@Override
public MovieReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.review_view, parent, false);

        return new MovieReviewAdapter.ViewHolder(v);
        }

@Override
public void onBindViewHolder(@NonNull MovieReviewAdapter.ViewHolder holder, int position) {

        holder.bind(position);

        }

@Override
public int getItemCount() {
        return reviews.size();
        }

class ViewHolder extends RecyclerView.ViewHolder {


    final RelativeLayout relativeLayout;
    final TextView userName;
    final TextView userReview;
    final ImageView userImage;

    ViewHolder(View itemView) {

        super(itemView);

        relativeLayout = itemView.findViewById(R.id.relativeLayout);
        userName = itemView.findViewById(R.id.userName);
        userReview = itemView.findViewById(R.id.userReview);
        userImage = itemView.findViewById(R.id.userImage);

    }

    void bind(int position) {

        final ParseReview review = reviews.get(position);
        userName.setText(review.getName());
        userReview.setText(review.getContent());
    }
}
}



package com.example.popularmoviesapp2.viewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmoviesapp2.R;
import com.example.popularmoviesapp2.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context mContext;
    private ArrayList<Movie> mMovieList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MovieAdapter(Context context, ArrayList<Movie> movieList) {
        mContext = context;
        mMovieList = movieList;

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.mainview_layout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie currentMovieItem = mMovieList.get(position);

        //Get Items to bind to view
        String movieItemPosterImageURL = currentMovieItem.getPosterImageURL();
        String movieItemTitle = currentMovieItem.getTitle();

        //Bind data to view
        holder.mTitleTextView.setText(movieItemTitle);
        Picasso.get().load(movieItemPosterImageURL).fit().into(holder.mPosterImageView);

    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public ImageView mPosterImageView;
        public TextView mTitleTextView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mPosterImageView = itemView.findViewById(R.id.poster_image);
            mTitleTextView = itemView.findViewById(R.id.movie_title);

            itemView.setOnClickListener(v -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(position);
                    }
                }
            });
        }
    }
}

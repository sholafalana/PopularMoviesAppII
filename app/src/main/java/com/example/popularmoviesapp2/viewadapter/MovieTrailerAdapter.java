package com.example.popularmoviesapp2.viewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.popularmoviesapp2.R;
import com.example.popularmoviesapp2.models.ParseTrailer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shola on 9/3/2018.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.ViewHolder> {

    private Context mContext;
    private List<ParseTrailer> parseTrailer;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int clickedItemIndex);
    }


    public MovieTrailerAdapter(List<ParseTrailer> parseTrailer, Context context, OnItemClickListener listener){

        this.parseTrailer = parseTrailer;
        this.mContext = context;
        this.mListener = listener;
    }
    @NonNull
    @Override
    public MovieTrailerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_view, parent, false);

        // View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycled_trailers_items, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTrailerAdapter.ViewHolder holder, int position) {

        holder.bind(position);

    }
    @Override
    public int getItemCount() {return parseTrailer.size();}

  /*  @Override
    public void onBindViewHolder(@NonNull MovieTrailerAdapterViewHolder holder, int position) {
        ParseTrailer currentTrailerItem = mTrailerList.get(position);

        //Get Items to bind to view
        String trailerName = currentTrailerItem.getName();

        //Bind data to view
        holder.mTrailerTextView.setText(trailerName);

    }

    @Override
    public int getItemCount() {

        return mTrailerList.size();
    }   */

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        final RelativeLayout relativeLayout;
        final TextView  mTrailerTextView;
        final ImageButton mTrailerImageButton;

        ViewHolder(View itemView) {

            super(itemView);

            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            mTrailerTextView = itemView.findViewById(R.id.trailerName);
            mTrailerImageButton = itemView.findViewById(R.id.playTrailer);

        }

        void bind(int position) {

            final ParseTrailer theTrailer = parseTrailer.get(position);
            mTrailerTextView.setText(theTrailer.getName());
            mTrailerImageButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int clickedPosition = getAdapterPosition();
            mListener.onItemClick(clickedPosition);
        }

    }


}


 /*  public class MovieTrailerAdapterViewHolder extends RecyclerView.ViewHolder {


        public TextView mTrailerTextView;
        // public ImageButton mTrailerImageButton;

        public MovieTrailerAdapterViewHolder(View itemView) {
            super(itemView);
            // mTrailerImageButton = itemView.findViewById(R.id.play);
            mTrailerTextView = itemView.findViewById(R.id.trailor_name_id);
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
}      */


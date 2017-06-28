package com.zenaufa.popularmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zenaufa.popularmovie.DetailActivity;
import com.zenaufa.popularmovie.R;
import com.zenaufa.popularmovie.model.Results;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends
        RecyclerView.Adapter<ContactAdapter.ViewHolder>{

    private Context context;
    private List<Results> movies;

    GestureDetector mGestureDetector;


    public ContactAdapter(Context context, List<Results> movies){
        super();
        this.context = context;
        this.movies = new ArrayList<>();
        if (movies != null)
            this.movies.addAll(movies);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.row_poster, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Results results = movies.get(position);
        final int pos = position;
        String url = "http://image.tmdb.org/t/p/w185/" + results.getPosterPath();
        Picasso.with(context)
                .load(url)
                .error(R.mipmap.ic_launcher)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("movieDesc", movies.get(pos).getOverview());
                intent.putExtra("movieTitle", movies.get(pos).getTitle());
                intent.putExtra("movieId", movies.get(pos).getId());
                intent.putExtra("movieImg", movies.get(pos).getPosterPath());
                intent.putExtra("movieRelease", movies.get(pos).getReleaseDate());
                intent.putExtra("movieRating", movies.get(pos).getVoteAverage());
                intent.putExtra("movieReview", movies.get(pos).getContent());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
        }
    }
}

package com.zenaufa.popularmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by AST-III-15-009 on 5/24/2017.
 */

public class DetailActivity extends AppCompatActivity {
    TextView title;
    TextView Release;
    TextView Description;
    TextView review;
    ImageView Poster;
    int id;
    RatingBar Rating;
    TextView Rating_text;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Poster = (ImageView) findViewById(R.id.poster);
        title = (TextView) findViewById(R.id.title);
        Release = (TextView) findViewById(R.id.releaseDate);
        Description = (TextView) findViewById(R.id.Description);
        Description.setMovementMethod(new ScrollingMovementMethod());
        Rating_text = (TextView) findViewById(R.id.rating_text);
        review = (TextView) findViewById(R.id.review);
        Picasso.with(this)
                .load("http://image.tmdb.org/t/p/w185/" + intent.getStringExtra("movieImg"))
                .placeholder(R.mipmap.ic_launcher)
                .into(Poster);
        title.setText(intent.getStringExtra("movieTitle"));
        Release.setText(intent.getStringExtra("movieRelease"));
        review.setText(intent.getStringExtra("movieReview"));
        Double voteAverage = intent.getDoubleExtra("movieRating", 12);
        id = intent.getIntExtra("movieId", 0);

        //rating
        String rating = String.valueOf(voteAverage);
        Rating_text.setText(rating);

        //description
        Description.setText(intent.getStringExtra("movieDesc"));

        //actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Movie Details");
        startService(intent);
    }
}
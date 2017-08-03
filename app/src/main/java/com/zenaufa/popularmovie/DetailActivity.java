package com.zenaufa.popularmovie;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.zenaufa.popularmovie.Db.MovieContract;

import com.squareup.picasso.Picasso;
import com.zenaufa.popularmovie.model.Results;

/**
 * Created by AST-III-15-009 on 5/24/2017.
 */

public class DetailActivity extends AppCompatActivity {
    TextView title;
    TextView Release;
    TextView Description;
    TextView review;
    ImageView Poster;
    Button favorite;
    int id;
    RatingBar Rating;
    TextView Rating_text;

    private Results results;

    private boolean isFavorite = false;

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
        favorite = (Button) findViewById(R.id.favorite);

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


        //favorite
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    deleteFromDatabase();
                    Toast.makeText(DetailActivity.this, getString(R.string.removed_from_favorites), Toast.LENGTH_SHORT).show();
                    favorite.setText(getString(R.string.add_favorite));
                } else {
                    insertToDatabase();
                    Toast.makeText(DetailActivity.this, getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show();
                    favorite.setText(getString(R.string.remove_favorite));
                }
            }
        });
    }

    private boolean isFavoriteMovie() {
        ContentResolver detailContentResolver = getContentResolver();
        Cursor cursor = detailContentResolver.query(
                MovieContract.MovieEntry.buildMovieUriWithMovieId(results.getId()),
                null,
                null,
                null,
                MovieContract.MovieEntry._ID + " ASC");

        if (cursor != null) {
            cursor.close();
            return cursor.getCount() > 0;
        } else {
            return false;
        }
    }

    private void insertToDatabase() {
        ContentResolver detailContentResolver = getContentResolver();

        ContentValues movieValues = new ContentValues();
        movieValues.put(MovieContract.MovieEntry.COLUMN_NAME_ID, results.getId());
        movieValues.put(MovieContract.MovieEntry.COLUMN_NAME_ORIGINAL_TITLE, results.getTitle());
        movieValues.put(MovieContract.MovieEntry.COLUMN_NAME_IMAGE_THUMBNAIL, results.getPosterPath());
        movieValues.put(MovieContract.MovieEntry.COLUMN_NAME_PLOT_SYNOPSIS, results.getOverview());
        movieValues.put(MovieContract.MovieEntry.COLUMN_NAME_VOTE_AVERAGE, results.getVoteAverage());
        movieValues.put(MovieContract.MovieEntry.COLUMN_NAME_RELEASE_DATE, results.getReleaseDate());

        detailContentResolver.insert(MovieContract.MovieEntry.CONTENT_URI, movieValues);
    }

    private void deleteFromDatabase() {
        ContentResolver detailContentResolver = getContentResolver();
        String[] selectionArguments = new String[]{String.valueOf(results.getId())};
        detailContentResolver.delete(MovieContract.MovieEntry.CONTENT_URI,
                MovieContract.MovieEntry.COLUMN_NAME_ID + " = ? ", selectionArguments);
    }
}

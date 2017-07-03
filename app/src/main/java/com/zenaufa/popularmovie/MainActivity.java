package com.zenaufa.popularmovie;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.zenaufa.popularmovie.R;


import com.zenaufa.popularmovie.adapter.ContactAdapter;
import com.zenaufa.popularmovie.model.Movies;
import com.zenaufa.popularmovie.service.NetworkCall;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvMovies;
    String status;
    private boolean mTwoPane;
    private boolean currentState = false;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    /*Start App*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        status = getString(R.string.popular);
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        rvMovies = (RecyclerView) findViewById(R.id.rv_movies);
        ContactAdapter adapter = new ContactAdapter(this, null);
        rvMovies.setAdapter(adapter);
        final int columns = getResources().getInteger(R.integer.gallery_columns);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL);
        rvMovies.setLayoutManager(gridLayoutManager);

        Intent popular = new Intent(this, NetworkCall.class);
        popular.setAction(NetworkCall.ACTION_CALL_API_POPULAR_MOVIE);
        startService(popular);
    }

    /* Menu */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuitemthatselected = item.getItemId();
        //Top
        if (menuitemthatselected == R.id.Tops){
            setContentView(R.layout.activity_main);

            rvMovies = (RecyclerView) findViewById(R.id.rv_movies);
            ContactAdapter adapter = new ContactAdapter(this, null);
            rvMovies.setAdapter(adapter);
            final int columns = getResources().getInteger(R.integer.gallery_columns);
            StaggeredGridLayoutManager gridLayoutManager =
                    new StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL);
            rvMovies.setLayoutManager(gridLayoutManager);

            Intent top = new Intent(this, NetworkCall.class);
            top.setAction(NetworkCall.ACTION_CALL_API_TOP_MOVIE);
            startService(top);
            status = getString(R.string.top);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(getString(R.string.top_movie));
        }
        //Popular
        if (menuitemthatselected == R.id.Populars){
            setContentView(R.layout.activity_main);

            rvMovies = (RecyclerView) findViewById(R.id.rv_movies);
            ContactAdapter adapter = new ContactAdapter(this, null);
            rvMovies.setAdapter(adapter);
            final int columns = getResources().getInteger(R.integer.gallery_columns);
            StaggeredGridLayoutManager gridLayoutManager =
                    new StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL);
            rvMovies.setLayoutManager(gridLayoutManager);

            Intent popular = new Intent(this, NetworkCall.class);
            popular.setAction(NetworkCall.ACTION_CALL_API_POPULAR_MOVIE);
            startService(popular);
            status=getString(R.string.popular);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(getString(R.string.popular_movie));
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void doThis(Movies movies) {
        ContactAdapter adapter = new ContactAdapter(this, movies.getMovies());
        rvMovies.setAdapter(adapter);
        final int columns = getResources().getInteger(R.integer.gallery_columns);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL);
        rvMovies.setLayoutManager(gridLayoutManager);
    }
}

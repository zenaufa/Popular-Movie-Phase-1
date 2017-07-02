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
    String SortStatus;
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
        SortStatus=getString(R.string.status_ascending);
    }

    /* Menu */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuitemthatselected = item.getItemId();

        //sort
        if (menuitemthatselected == R.id.Sort){
            setContentView(R.layout.activity_main);

            rvMovies = (RecyclerView) findViewById(R.id.rv_movies);
            ContactAdapter adapter = new ContactAdapter(this, null);
            rvMovies.setAdapter(adapter);
            final int columns = getResources().getInteger(R.integer.gallery_columns);
            StaggeredGridLayoutManager gridLayoutManager =
                    new StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL);
            rvMovies.setLayoutManager(gridLayoutManager);

            Intent Sort = new Intent(this, NetworkCall.class);
            startService(Sort);

            //ganti tombol ascending ke descending
            CharSequence title = item.getTitle();
            Log.d("onOptionsItemSelected", "menu_edit title = " + title);
            if(SortStatus==getString(R.string.status_descending)){
                if (status==getString(R.string.top)){
                    Sort.setAction(NetworkCall.ACTION_CALL_API_TOP_MOVIE);
                    startService(Sort);
                    // ganti ke descending
                    item.setTitle(getString(R.string.menu_descending));
                    SortStatus=getString(R.string.status_ascending);
                    ActionBar actionBar = getSupportActionBar();
                    actionBar.setTitle(getString(R.string.top_movie));
                }else {
                    Sort.setAction(NetworkCall.ACTION_CALL_API_POPULAR_MOVIE);
                    startService(Sort);
                    // ganti ke descending
                    item.setTitle(getString(R.string.menu_descending));
                    SortStatus=getString(R.string.status_ascending);ActionBar actionBar = getSupportActionBar();
                    actionBar.setTitle(getString(R.string.popular_movie));
                }
            } else {
                if (status==getString(R.string.top)){
                    Sort.setAction(NetworkCall.ACTION_CALL_API_TOP_MOVIE_ASC);
                    startService(Sort);
                    // ganti ke ascending
                    item.setTitle(getString(R.string.menu_ascending));
                    SortStatus=getString(R.string.status_descending);
                    ActionBar actionBar = getSupportActionBar();
                    actionBar.setTitle(getString(R.string.top_movie));
                }else{
                    Sort.setAction(NetworkCall.ACTION_CALL_API_POPULAR_MOVIE_ASC);
                    startService(Sort);
                    // ganti ke ascending
                    item.setTitle(getString(R.string.menu_ascending));
                    SortStatus=getString(R.string.status_descending);
                    ActionBar actionBar = getSupportActionBar();
                    actionBar.setTitle(getString(R.string.popular_movie));
                }
            }
        }
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
            SortStatus = getString(R.string.status_ascending);
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
            SortStatus=getString(R.string.status_ascending);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(getString(R.string.popular_movie));
        }
        return super.onOptionsItemSelected(item);
    }
    //Ganti title sort saat ganti category
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.Sort);
        if(SortStatus==getString(R.string.status_ascending)){
        item.setTitle(getString(R.string.menu_ascending));}else
        {item.setTitle(getString(R.string.menu_descending));}
        return super.onPrepareOptionsMenu(menu);
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

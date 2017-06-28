package com.zenaufa.popularmovie.service;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.zenaufa.popularmovie.model.Movies;
import com.zenaufa.popularmovie.retrofit.IMovieDBNetworkCall;
import com.zenaufa.popularmovie.retrofit.MovieDBNetworkCall;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NetworkCall extends IntentService {

    String call;
    String Status;

    private static final String TAG = NetworkCall.class.getSimpleName();

    public static final String ACTION_CALL_API_POPULAR_MOVIE = "ACTION_CALL_API_POPULAR_MOVIE";
    public static final String ACTION_CALL_API_TOP_MOVIE = "ACTION_CALL_API_TOP_MOVIE";
    public static final String ACTION_CALL_API_POPULAR_MOVIE_ASC = "ACTION_CALL_API_POPULAR_MOVIE_ASC";
    public static final String ACTION_CALL_API_TOP_MOVIE_ASC = "ACTION_CALL_API_TOP_MOVIE_ASC";
    public static final String ACTION_CALL_API_REVIEW = "ACTION_CALL_API_REVIEW";
    public NetworkCall() {
        super("NetworkCall");
    }

    @Override
    protected void onHandleIntent(Intent popular) {
        if (popular.getAction() != null && !TextUtils.isEmpty(popular.getAction())){
            String action = popular.getAction();
            if (action.equals(ACTION_CALL_API_POPULAR_MOVIE)){
                handleActionGetPopularMovies();
            }
            if (action.equals(ACTION_CALL_API_TOP_MOVIE)){
                handleActionGetTopMovies();
            }
            if (action.equals(ACTION_CALL_API_POPULAR_MOVIE_ASC)){
                handleActionGetPopularMoviesAsc();
            }
            if (action.equals(ACTION_CALL_API_TOP_MOVIE_ASC)){
                handleActionGetTopMoviesAsc();
            }
        }
    }

    private void handleActionGetPopularMovies() {
        IMovieDBNetworkCall callMovieAPI = MovieDBNetworkCall.getCalledMoviesAPI();
        Call<Movies> callPopularMovies = callMovieAPI.getPopularMovies();
        try {
            Movies movies = callPopularMovies.execute().body();
            Log.d(TAG, "size : "+ movies.getMovies().size());
            EventBus.getDefault().post(movies);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleActionGetTopMovies() {
        IMovieDBNetworkCall callMovieAPI = MovieDBNetworkCall.getCalledMoviesAPI();
        Call<Movies> callPopularMovies = callMovieAPI.getTopRatedMovies();
        try {
            Movies movies = callPopularMovies.execute().body();
            Log.d(TAG, "size : "+ movies.getMovies().size());
            EventBus.getDefault().post(movies);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleActionGetPopularMoviesAsc() {
        IMovieDBNetworkCall callMovieAPI = MovieDBNetworkCall.getCalledMoviesAPI();
        Call<Movies> callMovieAPIPopularMoviesAsc = callMovieAPI.getPopularMoviesAsc();
        try {
            Movies movies = callMovieAPIPopularMoviesAsc.execute().body();
            Log.d(TAG, "size : "+ movies.getMovies().size());
            EventBus.getDefault().post(movies);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleActionGetTopMoviesAsc() {
        IMovieDBNetworkCall callMovieAPI = MovieDBNetworkCall.getCalledMoviesAPI();
        Call<Movies> callPopularMovies = callMovieAPI.getTopRatedMoviesAsc();
        try {
            Movies movies = callPopularMovies.execute().body();
            Log.d(TAG, "size : "+ movies.getMovies().size());
            EventBus.getDefault().post(movies);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

package com.zenaufa.popularmovie.retrofit;

import com.zenaufa.popularmovie.BuildConfig;
import com.zenaufa.popularmovie.R;
import com.zenaufa.popularmovie.model.Movies;

import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by ulfiaizzati on 10/13/16.
 */

public interface IMovieDBNetworkCall {

    final String TMDB_API_KEY = BuildConfig.THE_MOVIE_DB_API_TOKEN;
    @GET("popular?api_key="+TMDB_API_KEY)
    Call<Movies> getPopularMovies();

    @GET("top_rated?api_key="+TMDB_API_KEY)
    Call<Movies> getTopRatedMovies();
}

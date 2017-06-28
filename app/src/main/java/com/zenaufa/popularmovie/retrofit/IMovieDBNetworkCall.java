package com.zenaufa.popularmovie.retrofit;

import com.zenaufa.popularmovie.BuildConfig;
import com.zenaufa.popularmovie.model.Movies;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ulfiaizzati on 10/13/16.
 */

public interface IMovieDBNetworkCall {

    final String TMDB_API_KEY = BuildConfig.THE_MOVIE_DB_API_TOKEN;

    @GET("?api_key="+TMDB_API_KEY+"&sort_by=popularity.desc")
    Call<Movies> getPopularMovies();

    @GET("?api_key="+TMDB_API_KEY+"&sort_by=popularity.asc")
    Call<Movies> getPopularMoviesAsc();

    @GET("?api_key="+TMDB_API_KEY+"&without_genres=99,10755&vote_count.gte=75&sort_by=vote_average.desc")
    Call<Movies> getTopRatedMovies();

    @GET("?api_key="+TMDB_API_KEY+"&without_genres=99,10755&vote_count.gte=75&sort_by=vote_average.asc")
    Call<Movies> getTopRatedMoviesAsc();
}

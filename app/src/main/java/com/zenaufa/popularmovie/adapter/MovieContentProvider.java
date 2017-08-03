package com.zenaufa.popularmovie.adapter;

import android.content.UriMatcher;
import android.net.Uri;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.zenaufa.popularmovie.Db.MovieContract;
import com.zenaufa.popularmovie.R;

/**
 * Created by Zen Aufa on 8/1/2017.
 */

public class MovieContentProvider extends ContentProvider {
    public static final int CODE_MOVIE_FAVORITES = 100;
    public static final int CODE_MOVIE_WITH_ID = 101;

    private static final UriMatcher URI_MATCHER = buildUriMatcher();

    private MovieDbHelper movieDbHelper;

    private Context context;

    public static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.MovieEntry.PATH_MOVIE, CODE_MOVIE_FAVORITES);
        matcher.addURI(authority, MovieContract.MovieEntry.PATH_MOVIE + "/#", CODE_MOVIE_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate(){
        movieDbHelper = new MovieDbHelper(getContext());
        context = getContext();
        return true;
    }

    @Override
    public int bulkInsert (@NonNull Uri uri, @NonNull ContentValues[] values){
        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();

        switch (URI_MATCHER.match(uri)){
            case CODE_MOVIE_FAVORITES :
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, value);
                        if (_id != 1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                    ;
                }
                finally {
                    db.endTransaction();
                }

                if (rowsInserted >0){
                    context.getContentResolver().notifyChange(uri, null);
                    return rowsInserted;}

            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        switch (URI_MATCHER.match(uri)) {
            case CODE_MOVIE_FAVORITES:
                Uri resultUri = null;
                long id = movieDbHelper.getWritableDatabase().insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                if (id != -1) {
                    resultUri = MovieContract.MovieEntry.buildMovieUriWithMovieId(values.getAsInteger(MovieContract.MovieEntry.COLUMN_NAME_ID));
                }
                if (resultUri != null) {
                    context.getContentResolver().notifyChange(resultUri, null);
                }
                return resultUri;

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
    }

    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;

        switch (URI_MATCHER.match(uri)){
            case CODE_MOVIE_WITH_ID: {
                String movieId = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{movieId};

                cursor = movieDbHelper.getReadableDatabase().query(MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        MovieContract.MovieEntry.COLUMN_NAME_ID + " = ? ",
                        selectionArguments,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case CODE_MOVIE_FAVORITES:
                cursor = movieDbHelper.getReadableDatabase().query(MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            default :
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
        cursor.setNotificationUri(context.getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs){
        int numRowsDeleted;
        if (null == selection) selection = "1";

        switch (URI_MATCHER.match(uri)){
            case CODE_MOVIE_FAVORITES:
                numRowsDeleted = movieDbHelper.getWritableDatabase().delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default :
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        if (numRowsDeleted != 0){
            context.getContentResolver().notifyChange(uri, null);
        }
        return numRowsDeleted;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException(context.getString(R.string.content_provider_not_implemented));
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new RuntimeException(context.getString(R.string.content_provider_not_implemented));
    }

}

package com.zenaufa.popularmovie.adapter;

/**
 * Created by Zen Aufa on 8/1/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zenaufa.popularmovie.Db.MovieContract;

public class MovieDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        final String SQL_CREATE_TABLE = "CREATE TABLE" + MovieContract.MovieEntry.TABLE_NAME + " ( " +
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContract.MovieEntry.COLUMN_NAME_ORIGINAL_TITLE + "INTEGER NOT NULL," +
                MovieContract.MovieEntry.COLUMN_NAME_IMAGE_THUMBNAIL + "TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_NAME_PLOT_SYNOPSIS +  " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_NAME_VOTE_AVERAGE + "TEXT NOT NULL, " +
                " UNIQUE (" + MovieContract.MovieEntry.COLUMN_NAME_ID + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}

package com.example.eslam.finalmovies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by eslam on 11/18/2016.
 */

public class MoviesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 5;


    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String TABLE_CREATE = "CREATE TABLE " + MoviedbEntery.TABLE_NAME + " (" +
                MoviedbEntery.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  " +
                MoviedbEntery.COLUMN_TITLE + " TEXT, " +
                MoviedbEntery.COLUMN_MOVIE_POPULARTY + " DOUBLE, " +
                MoviedbEntery.COLUMN_POSTER_URL + " TEXT, " +
                MoviedbEntery.DISC + " TEXT, " +
                MoviedbEntery.IDI + " TEXT, " +
                MoviedbEntery.COLUMN_RATING + " INTEGER, " +
                MoviedbEntery.COLUMN_RELEASE_DATE + " TEXT);";

        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviedbEntery.TABLE_NAME);
        onCreate(db);
    }

    public static class MoviedbEntery implements BaseColumns {

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_MOVIE_POPULARTY = "popularity";
        public static final String COLUMN_POSTER_URL = "poster_url";
        public static final String DISC = "Disc";
        public static final String IDI = "IDI";
    }

}

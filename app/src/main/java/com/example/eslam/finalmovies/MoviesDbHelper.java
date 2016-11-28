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

        final String TABLE_CREATE  = "CREATE TABLE " + MoviedbEntery.TABLE_NAME + " (" +
                MoviedbEntery.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  " +
                MoviedbEntery.COLUMN_TITLE + " TEXT NOT NULL, " +
                MoviedbEntery.COLUMN_MOVIE_POPULARTY + " DOUBLE, " +
                MoviedbEntery.COLUMN_POSTER_URL + " TEXT NOT NULL, " +
                MoviedbEntery.DISC + " TEXT NOT NULL, " +
                MoviedbEntery.IDI + " TEXT NOT NULL, " +
                MoviedbEntery.COLUMN_RATING + " INTEGER, " +
                MoviedbEntery.COLUMN_RELEASE_DATE + " TEXT NOT NULL);";

        /*String createTable = generateCreateTable(MoviedbEntery.TABLE_NAME, new String[]{
                MoviedbEntery.COLUMN_ID,
                MoviedbEntery.COLUMN_MOVIE_POPULARTY,
                MoviedbEntery.COLUMN_POSTER_URL,
                MoviedbEntery.COLUMN_RATING,
                MoviedbEntery.DISC,
                MoviedbEntery.COLUMN_TITLE,
                MoviedbEntery.COLUMN_RELEASE_DATE
                //MoviedbEntery.COLUMN_MOVIE_POSTER_PATH


        });*/
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+MoviedbEntery.TABLE_NAME);
        onCreate(db);
    }

  /*  private String generateCreateTable(String tableName, String[] columnNames) {
        StringBuilder builder = new StringBuilder("CREATE TABLE ");
        builder.append(tableName).append("(");
        builder.append(BaseColumns._ID);
        builder.append(" INT PRIMARY KEY, ");
        for (int i = 1; i < columnNames.length; i++) {
            builder.append(columnNames[i]);
            if (i != columnNames.length - 1) {
                builder.append(", ");
            }
        }
        builder.append(");");
        return builder.toString();
    }*/

    public static class MoviedbEntery implements BaseColumns{

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_MOVIE_POPULARTY = "popularity";
        //public static final String COLUMN_MOVIE_POSTER_PATH = "poster_path";
        public static final String COLUMN_POSTER_URL = "poster_url";
        public static final String DISC = "Disc";
        public static final String IDI= "IDI";
    }

}

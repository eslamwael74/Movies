package com.example.eslam.finalmovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by eslam on 21/11/16.
 */

public class db {
    private static MoviesDbHelper dpH;
    private SQLiteDatabase SQ_db;
    private static db instance;
    Context context;
    Cursor cursor;


    public static db getInstance() {
        if (instance == null) {
            instance = new db();
            dpH = new MoviesDbHelper(Apple.getana());
            instance.open();
        }
        return instance;
    }

    private db() {
    }

    private void open() {
        SQ_db = dpH.getWritableDatabase();
    }

    private ContentValues getContentFromFlickerModel(Movie Model) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(MoviesDbHelper.MoviedbEntery.COLUMN_ID, Model.getID());
        contentValues.put(MoviesDbHelper.MoviedbEntery.COLUMN_TITLE, Model.getTitle());
        contentValues.put(MoviesDbHelper.MoviedbEntery.DISC, Model.getDescription());
        contentValues.put(MoviesDbHelper.MoviedbEntery.COLUMN_RATING, Model.getVote());
        contentValues.put(MoviesDbHelper.MoviedbEntery.COLUMN_POSTER_URL, Model.getPicture());
        contentValues.put(MoviesDbHelper.MoviedbEntery.COLUMN_RELEASE_DATE, Model.getDate());
        contentValues.put(MoviesDbHelper.MoviedbEntery.COLUMN_MOVIE_POPULARTY, Model.getPopularity());
        contentValues.put(MoviesDbHelper.MoviedbEntery.IDI, Model.getIDI());

        return contentValues;


    }

    private Movie getMovieFromCursor(Cursor cursor) {
        Movie movie = new Movie();
        movie.setID(cursor.getInt(cursor.getColumnIndex(MoviesDbHelper.MoviedbEntery.COLUMN_ID)));
        movie.setDescription(cursor.getString(cursor.getColumnIndex(MoviesDbHelper.MoviedbEntery.DISC)));
        movie.setVote(cursor.getInt(cursor.getColumnIndex(MoviesDbHelper.MoviedbEntery.COLUMN_RATING)));
        movie.setDate(cursor.getString(cursor.getColumnIndex(MoviesDbHelper.MoviedbEntery.COLUMN_RELEASE_DATE)));
        movie.setPicture(cursor.getString(cursor.getColumnIndex(MoviesDbHelper.MoviedbEntery.COLUMN_POSTER_URL)));
        movie.setPopularity(cursor.getDouble(cursor.getColumnIndex(MoviesDbHelper.MoviedbEntery.COLUMN_MOVIE_POPULARTY)));
        movie.setTitle(cursor.getString(cursor.getColumnIndex(MoviesDbHelper.MoviedbEntery.COLUMN_TITLE)));
        movie.setIDI(cursor.getInt(cursor.getColumnIndex(MoviesDbHelper.MoviedbEntery.IDI)));
        return movie;
    }

    public void removeMovie(Movie movie) {
        SQ_db.delete(MoviesDbHelper.MoviedbEntery.TABLE_NAME, MoviesDbHelper.MoviedbEntery.COLUMN_TITLE + "=?",
                new String[]{String.valueOf(movie.getTitle())});
    }

    public ArrayList<Movie> getFav() {
        ArrayList<Movie> movs = new ArrayList<>();
        Cursor cursor = Apple.getana().getContentResolver().query(ContentProvider.CONTENT_URI,
                null,null,null,null );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Movie Model = getMovieFromCursor(cursor);
            movs.add(Model);
            cursor.moveToNext();
        }
        cursor.close();
        return movs;
    }

    public void insertMovie(Movie modle) {
        ContentValues contentValues = getContentFromFlickerModel(modle);
        long id = dpH.getWritableDatabase().insert(MoviesDbHelper.MoviedbEntery.TABLE_NAME, null, contentValues);
        Cursor cursor = SQ_db.query(MoviesDbHelper.MoviedbEntery.TABLE_NAME, null
                , MoviesDbHelper.MoviedbEntery.COLUMN_ID + " = " + id, null, null, null, null);
        //cursor.moveToFirst();
        if (cursor != null && cursor.moveToFirst()) {
            Movie movie = getMovieFromCursor(cursor);
            modle.setID(movie.getID());
            cursor.close();
        }
    }
   /* public void deleteMovies(){
        Databases.delete(MoviesDbHelper.MoviedbEntery.TABLE_NAME,null,null);
    }*/
}

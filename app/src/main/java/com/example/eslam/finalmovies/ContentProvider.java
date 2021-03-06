package com.example.eslam.finalmovies;

import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Eslam Wael on 10/24/2017.
 */

public class ContentProvider extends android.content.ContentProvider {

    Context  context;

    MoviesDbHelper moviesDbHelper;
    private static final String TAG = "ContentProvider";
    static final int MOVIES = 0;
    static final int MOVIE = 1;

    static String PROVIDER_NAME = "com.example.eslam.finalmovies.ContentProvider";
    static String URL = "content://" + PROVIDER_NAME + "/movies";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    public static String id = "id";
    public static final String TITLE = "title";
    public static final String RATING = "rating";
    public static final String RELEASE_DATE = "release_date";
    public static final String MOVIE_POPULARTY = "popularity";
    public static final String POSTER_URL = "poster_url";
    public static final String DISC = "Disc";

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "movies", MOVIES);
        uriMatcher.addURI(PROVIDER_NAME, "movies/#", MOVIE);
    }

    private static HashMap<String, String> Movies;


    @Override
    public boolean onCreate() {
        moviesDbHelper = new MoviesDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase db = moviesDbHelper.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(MoviesDbHelper.MoviedbEntery.TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case MOVIES:
                //do here :D
                break;
            case MOVIE:
                String id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(MoviesDbHelper.MoviedbEntery.COLUMN_ID + "=" + id);
                break;
            default:
                throw new IllegalArgumentException("faild load URI: " + uri);
        }

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        SQLiteDatabase sqLiteDatabase = moviesDbHelper.getWritableDatabase();
        long id;

        switch (uriMatcher.match(uri)) {
            case MOVIES:
                //listMoviews

                id = sqLiteDatabase.insert(MoviesDbHelper.MoviedbEntery.TABLE_NAME, null, values);
                Log.d(TAG, "insert id: " + id);

                break;
            default:
                throw new IllegalArgumentException("faild load URI: " + uri);

        }
        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(CONTENT_URI + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = moviesDbHelper.getWritableDatabase();
        int dRow;
        switch (uriMatcher.match(uri)) {
            case MOVIES:
                dRow =  db.delete(MoviesDbHelper.MoviedbEntery.TABLE_NAME,selection,selectionArgs);
                //do here
                break;
            case MOVIE:
                 id = uri.getLastPathSegment();
                dRow =  db.delete(MoviesDbHelper.MoviedbEntery.TABLE_NAME,MoviesDbHelper.MoviedbEntery.COLUMN_ID+ "="+id, null);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return dRow;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase db = moviesDbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case MOVIES:
                //do here :D
                break;
            case MOVIE:
                id = uri.getPathSegments().get(1);
                selection = MoviesDbHelper.MoviedbEntery.COLUMN_ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int updateCount = db.update(MoviesDbHelper.MoviedbEntery.TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }
}

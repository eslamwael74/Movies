package com.example.eslam.finalmovies;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;
import android.view.LayoutInflater;


/**
 * Created by eslam on 10/17/2016.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {
    private Context context;
    ArrayList<Movie> listOfMovie;
    public static final String URL_IMAGES = "http://image.tmdb.org/t/p/w185/";

    public MovieAdapter(Context context, ArrayList<Movie> objects) {
        super(context, 0, objects);
        this.context = context;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list, parent, false);
        }
        Movie moviedb = getItem(position);
        moviedb.setFav(moviedb.isFavD());
        imageView = (ImageView) convertView.findViewById(R.id.ImageView);
        Picasso.with(context)
                .load(URL_IMAGES + moviedb.getPicture())
                .placeholder(R.drawable.poster_place_holder)
                .into(imageView);

        return convertView;
    }



}



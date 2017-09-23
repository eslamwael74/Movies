package com.example.eslam.finalmovies;

/**
 * Created by eslam on 11/25/2016.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.widget.TextView;

public class ReviewsAdapter extends ArrayAdapter<String> {

    ArrayList<String> riviewList;
    Context context;

    public ReviewsAdapter(Context context, ArrayList<String> reviews) {
        super(context, 0, reviews);
        riviewList = reviews;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review, parent, false);
        }
        TextView trailerText = (TextView) convertView.findViewById(R.id.review_name);
        String x = riviewList.get(position);

        trailerText.setText("Reviews :\n" + x);

        return convertView;
    }
}

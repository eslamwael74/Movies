package com.example.eslam.finalmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.eslam.finalmovies.R;
import com.example.eslam.finalmovies.Trailer;
import java.util.List;
import java.util.ArrayList;
import android.view.LayoutInflater;
import android.widget.TextView;

/**
 * Created by eslam on 11/19/2016.
 */

public class TrailerAdapter extends ArrayAdapter<String> {
    ArrayList<String>T;
    Context context ;
    public TrailerAdapter(Context context, ArrayList<String> trailers) {
        super(context,0 ,trailers);
        T=trailers;
        this.context=context;
    }



    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trailer,parent,false);
        }
        TextView trailerText = (TextView) convertView.findViewById(R.id.trailer_name);

        int x = position+1;
        trailerText.setText("Trailer : " +x);
        trailerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(T.get(position)));
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}

package com.example.eslam.finalmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Eslam Wael on 10/26/2017.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.MyViewHolder> {

    ArrayList<String> trailers;
    FragmentActivity fragmentActivity;

    public TrailersAdapter(ArrayList<String> trailers, FragmentActivity fragmentActivity) {
        this.trailers = trailers;
        this.fragmentActivity = fragmentActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrailersAdapter.MyViewHolder(LayoutInflater.from(fragmentActivity)
                .inflate(R.layout.trailer, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        int x = position + 1;
        holder.trailerText.setText("Trailer : " + x);
        holder.trailerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailers.get(position)));
                fragmentActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView trailerText;

        public MyViewHolder(View itemView) {
            super(itemView);

            trailerText = (TextView) itemView.findViewById(R.id.trailer_name);

        }
    }

}



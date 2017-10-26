package com.example.eslam.finalmovies;

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

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    ArrayList<String> riviewList;
    FragmentActivity fragmentActivity;

    public ReviewAdapter(ArrayList<String> riviewList, FragmentActivity fragmentActivity) {
        this.riviewList = riviewList;
        this.fragmentActivity = fragmentActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReviewAdapter.MyViewHolder(LayoutInflater.from(fragmentActivity)
                .inflate(R.layout.review, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String x = riviewList.get(position);

        holder.trailerText.setText("Reviews :\n" + x);

    }

    @Override
    public int getItemCount() {
        return riviewList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView trailerText;

        public MyViewHolder(View itemView) {
            super(itemView);

            trailerText = (TextView) itemView.findViewById(R.id.review_name);

        }
    }

}



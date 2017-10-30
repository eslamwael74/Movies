package com.example.eslam.finalmovies;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by eslam on 11/25/2016.
 */

public class FavListFragment extends Fragment {

    GridView gridView;
    public View view;
    ArrayList<Movie> moviess;
    Parcelable parcelable;
    MovieAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_fav, container, false);
        gridView = (GridView) view.findViewById(R.id.gridview_fav);

        if (savedInstanceState == null) {

            //in getFav i retrieve it directly (using getWritableDatabase().query) please check getFav Function
            updateFav(db.getInstance().getFav());
            moviess = db.getInstance().getFav();

        } else {
            moviess = (ArrayList<Movie>) savedInstanceState.getSerializable("mov");
            updateFav(moviess);
            parcelable = savedInstanceState.getParcelable("stateh");
            //  gridView.onRestoreInstanceState(parcelable);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("mov", moviess);

        outState.putParcelable("stateh", gridView.onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    public void updateFav(final ArrayList<Movie> movies) {

        adapter = new MovieAdapter(getActivity(), movies);

        gridView.setAdapter(adapter);
        if (!isTablet(getActivity())) {

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), MovieDetails.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Mov", movies.get(i));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            });

        } else {

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Fragment f = new MovieDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Mov", movies.get(position));
                    f.setArguments(bundle);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_Det, f);
                    fragmentTransaction.commit();

                }
            });
        }
    }

    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        boolean large = ((context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }
}


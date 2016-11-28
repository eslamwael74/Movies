package com.example.eslam.finalmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by eslam on 10/17/2016.
 */

public class MovieDetails extends AppCompatActivity {

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        final Movie movieIntent = (Movie) getIntent().getExtras().getSerializable("Mov");

        checkNetworkAvailable();
        if(networkStatus == true) {
            Fragment f = new MovieDetailsFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.details_fragment_movies, f);
            fragmentTransaction.commit();
        }
        else{

            Toast.makeText(this, "Your Network is Not Available", Toast.LENGTH_LONG).show();
            Fragment f = new MovieDetailsFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.details_fragment_movies, f);
            fragmentTransaction.commit();

        }



//         ActionBar actionBar = getSupportActionBar();
//         actionBar.setDisplayOptions(actionBar.getDisplayOptions()
//                 | ActionBar.DISPLAY_SHOW_CUSTOM);

//         ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
//                 ActionBar.LayoutParams.WRAP_CONTENT,
//                 ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
//                 | Gravity.CENTER_VERTICAL);
//         layoutParams.rightMargin = 40;
//         imageView.setLayoutParams(layoutParams);
//         actionBar.setCustomView(imageView)

    }
    @Override
    protected void onResume() {

        if(networkStatus != true) {

            Toast.makeText(this, "Your Network is Not Available", Toast.LENGTH_LONG).show();
        }
        super.onResume();

    }
    private boolean networkStatus;
    private void checkNetworkAvailable() {
        networkStatus = false;
        try{
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                networkStatus = true;
            }else {
                netInfo = cm.getNetworkInfo(1);
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    networkStatus = true;
            }
        }catch(Exception e){
            e.printStackTrace();
            networkStatus = false;
        }
    }
//
//    @Override
//    public void onBackPressed() {
//
//        if(!isTablet(getApplicationContext())) {
//            Intent intent = new Intent(this,MainActivity.class);
//            startActivity(intent);
//
//        }
//        else
//        {
//            Fragment f = new FavListFragment();
//            FragmentManager fm = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fm.beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_movies2, f);
//            fragmentTransaction.commit();
//        }
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//    }

    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        boolean large = ((context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }



}
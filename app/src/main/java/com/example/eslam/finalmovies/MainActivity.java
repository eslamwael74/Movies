package com.example.eslam.finalmovies;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkNetworkAvailable();
        if(networkStatus == true) {

            if (!isTablet(getApplicationContext())) {
                Fragment f = new MainFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_movies, f);
                fragmentTransaction.commit();
            } else {
                Fragment f = new MainFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_movies2, f);
                fragmentTransaction.commit();
            }
        }
        else{

            Toast.makeText(this, "Your Network is Not Available", Toast.LENGTH_LONG).show();
        }

    }

//    @Override
//    public void onBackPressed() {
//        System.exit(0);
//        this.finish();
//
//    }


    @Override
    protected void onResume() {

        if(networkStatus != true) {

            Toast.makeText(this, "Your Network is Not Available", Toast.LENGTH_LONG).show();
        }
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_details,menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_settings){
            Intent opIntent = new Intent(this,SettingsActivity.class);
            startActivity(opIntent);
            return true;

        }
        else if (id==R.id.action_Fav){
            if(!isTablet(getApplicationContext())) {
                Intent intent = new Intent(this,FavList.class);
                startActivity(intent);
                return true;
            }
            else
            {
                Fragment f = new FavListFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_movies2, f);
                fragmentTransaction.commit();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        boolean large = ((context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
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
}

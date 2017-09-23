package com.example.eslam.finalmovies;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by eslam on 11/21/2016.
 */

public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstantceState) {
        super.onCreate(savedInstantceState);
        setContentView(R.layout.activity_pref);
        getFragmentManager().beginTransaction().replace(R.id.framee,
                new PrefFragment()).commit();

    }


}


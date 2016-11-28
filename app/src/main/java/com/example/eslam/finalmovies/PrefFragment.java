package com.example.eslam.finalmovies;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by eslam on 11/7/2016.
 */

public class PrefFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        setHasOptionsMenu(true);

    }
}

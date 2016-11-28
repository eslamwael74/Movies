package com.example.eslam.finalmovies;

import android.app.Application;

/**
 * Created by eslam on 11/22/2016.
 */
public class Apple extends Application {
    public static Apple ana;

    @Override
    public void onCreate() {
        super.onCreate();
        ana=this;
    }
    public static Apple getana(){return ana;}
}

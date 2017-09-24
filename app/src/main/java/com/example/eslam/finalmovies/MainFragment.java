package com.example.eslam.finalmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

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
 * Created by eslam on 11/21/2016.
 */

public class MainFragment extends Fragment {

    static String TAG = "Movie Results";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        if (savedInstanceState == null) {
            SyncDb syncdb = new SyncDb();
            syncdb.execute();
        }
        return view;
    }

    @Nullable
    private void update(final ArrayList<Movie> movies) {
        GridView gridView = (GridView) getActivity().findViewById(R.id.gridview);
        MovieAdapter adapter = new MovieAdapter(getContext(), movies);
        gridView.setAdapter(adapter);
        if (!isTablet(getContext())) {
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

    @Override
    public void onResume() {
//        Toast.makeText(getActivity(),"Data Submit Successfully", Toast.LENGTH_LONG).show();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String order = prefs.getString("PREF_LIST", "popular");
        if (!order.equals(TAG)) {
            SyncDb db = new SyncDb();
            db.execute();
        }
        super.onResume();
    }
    public class SyncDb extends AsyncTask<URL, Void, ArrayList<Movie>> {
        public final String urll = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=26f93e16f5f1dadf6c0c3c17462efcc6";
        public final String urllTV = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key=26f93e16f5f1dadf6c0c3c17462efcc6";


        @Override
        protected ArrayList<Movie> doInBackground(URL... paramas) {


            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String order = prefs.getString("PREF_LIST", "popular");

            if (order.equals("popular")) {
                URL url = createURL(urll);
                String json = URLResult(url);
                ArrayList<Movie> movies = jsonParser(json);
                TAG = "popular";
                return movies;

            } else {
                URL url = createURL(urllTV);
                String json = URLResult(url);
                ArrayList<Movie> movies = jsonParser(json);
                TAG = "top_rated";
                return movies;
            }

        }


        @Override
        protected void onPostExecute(ArrayList<Movie> list) {
            super.onPostExecute(list);
            update(list);

        }


        private String URLResult(URL webAddress) {
            InputStream inputStream = null;
            String json = null;
            try {
                //URL url = new URL(webAddress);
                HttpURLConnection connection = (HttpURLConnection) webAddress.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                inputStream = connection.getInputStream();
                json = readFromStream(inputStream);
                connection.disconnect();
                inputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }


        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        private URL createURL(String S) {
            URL url = null;
            try {
                url = new URL(S);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return url;
        }

        private ArrayList<Movie> jsonParser(String s) {
            ArrayList<Movie> movies = new ArrayList<>();
            try {

                JSONObject mObject = new JSONObject(s);
                JSONArray resultsArray = mObject.getJSONArray("results");
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject indexObject = resultsArray.getJSONObject(i);
                    Movie indexMovie = new Movie(indexObject.getInt("id"),
                            indexObject.getString("release_date"),
                            indexObject.getString("overview"), indexObject.getString("poster_path"),
                            indexObject.getDouble("popularity"),
                            indexObject.getString("title"),
                            indexObject.getInt("vote_average"), false, indexObject.getInt("id"));

                    movies.add(indexMovie); // Add each item to the list

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "Error!!", e);
            }
            return movies;
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
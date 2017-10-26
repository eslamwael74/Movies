package com.example.eslam.finalmovies;


import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by eslam on 11/25/2016.
 */

public class MovieDetailsFragment extends Fragment {

    private View view;
    Movie movieIntent;
    RecyclerView listView1, listView;
    NestedScrollView scrollView;
    ArrayList<String> vList;
    ArrayList<String> rList;

    Parcelable reviewState, trailerState;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.movie_details_fragment, container, false);

        listView1 = (RecyclerView) view.findViewById(R.id.review_names);
        scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);

        listView = (RecyclerView) view.findViewById(R.id.trailer_names);


//        scrollView = (ScrollView) view.findViewById(R.id.review_names);

        if (!isTablet(getContext()))
            movieIntent = (Movie) getActivity().getIntent().getExtras().getSerializable("Mov");
        else
            movieIntent = (Movie) getArguments().getSerializable("Mov");
        final ImageView imageView = (ImageView) view.findViewById(R.id.fav_btn);
        //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if (movieIntent.isFav())
            imageView.setImageResource(R.drawable.un_fav);
        else
            imageView.setImageResource(R.drawable.fav);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movieIntent.isFav()) {
                    imageView.setImageResource(R.drawable.fav);
                    db.getInstance().removeMovie(movieIntent);
                    movieIntent.setFav(false);
                } else {
                    imageView.setImageResource(R.drawable.un_fav);
                    db.getInstance().insertMovie(movieIntent);
                    movieIntent.setFav(true);
                }
            }
        });

        //urll = new String("http://api.themoviedb.org/3/movie/" + movieIntent.getIDI() + "/videos?sort_by=popularity.desc&api_key=26f93e16f5f1dadf6c0c3c17462efcc6");
        if (movieIntent != null) {
            //Title
            TextView title = (TextView) view.findViewById(R.id.movie_title);
            title.setText(movieIntent.getTitle());

            //ImagView
            ImageView image = (ImageView) view.findViewById(R.id.detail_image);
            Picasso.with(getActivity()).load(MovieAdapter.URL_IMAGES + movieIntent
                    .getPicture())
                    .placeholder(R.drawable.poster_place_holder)
                    .into(image);

            //Date
            TextView date = (TextView) view.findViewById(R.id.date);
            date.setText(movieIntent.getDate());

            //popularity
            TextView popularity = (TextView) view.findViewById(R.id.Popularity);
            popularity.setText("popularity: " + movieIntent.getPopularity());

            //Vote
            TextView vote = (TextView) view.findViewById(R.id.vote);
            vote.setText(movieIntent.getVote() + "");

            TextView overView = (TextView) view.findViewById(R.id.description);
            overView.setText(movieIntent.getDescription() + "");


        } else {
            Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_LONG).show();
        }

        if (savedInstanceState == null) {
            SyncDb ddb = new SyncDb();
            String urlV = new String("http://api.themoviedb.org/3/movie/" + movieIntent.getIDI() + "/videos?sort_by=popularity.desc&api_key=26f93e16f5f1dadf6c0c3c17462efcc6");

            try {
                URL url1 = new URL(urlV);
                if (savedInstanceState == null)
                    ddb.execute(url1);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            SyncR ddR = new SyncR();
            String urlR = new String("http://api.themoviedb.org/3/movie/" + movieIntent.getIDI() + "/reviews?sort_by=popularity.desc&api_key=26f93e16f5f1dadf6c0c3c17462efcc6");
            try {
                URL url2 = new URL(urlR);
                if (savedInstanceState == null)
                    ddR.execute(url2);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            movieIntent = (Movie) savedInstanceState.getSerializable("movi");
            rList = (ArrayList<String>) savedInstanceState.getSerializable("rList");


            updateR(rList);
            vList = (ArrayList<String>) savedInstanceState.getSerializable("vList");
            updateV(vList);
        }
        return view;
    }

    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            reviewState = savedInstanceState.getParcelable("review");
            trailerState = savedInstanceState.getParcelable("trailer");
            int[] position = savedInstanceState.getIntArray("SCROLL_POSITION");
            if (position != null)
                scrollView.post(() -> scrollView.scrollTo(position[0], position[1]));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("vList", vList);
        outState.putSerializable("rList", rList);

        outState.putParcelable("trailer", listView.getLayoutManager().onSaveInstanceState());
        outState.putParcelable("review", listView1.getLayoutManager().onSaveInstanceState());

        outState.putSerializable("movi", movieIntent);
        outState.putIntArray("SCROLL_POSITION",
                new int[]{scrollView.getScrollX(), scrollView.getScrollY()});
    }

    public void updateV(ArrayList<String> list) {
        TrailersAdapter trailerAdapter = new TrailersAdapter(list, getActivity());
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.setAdapter(trailerAdapter);
        if (reviewState != null)
            listView.getLayoutManager().onRestoreInstanceState(reviewState);

    }

    public void updateR(ArrayList<String> list) {

        ReviewAdapter reviewsAdapter = new ReviewAdapter(list, getActivity());
        listView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView1.setAdapter(reviewsAdapter);
        if (trailerState != null)
            listView1.getLayoutManager().onRestoreInstanceState(trailerState);


    }

    public class SyncDb extends AsyncTask<URL, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(URL... paramas) {

            String json = URLResult(paramas[0]);
            ArrayList<String> movies = jsonParser(json);

            return movies;
        }

        @Override
        protected void onPostExecute(ArrayList<String> list) {
            super.onPostExecute(list);
            vList = list;
            updateV(list);
        }

        private String URLResult(URL webAddress) {
            InputStream inputStream = null;
            String json = null;
            try {
                //URL url = new URL(webAddress);
                if (webAddress != null) {
                    HttpURLConnection connection = (HttpURLConnection) webAddress.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    inputStream = connection.getInputStream();
                    json = readFromStream(inputStream);
                    connection.disconnect();
                    inputStream.close();
                }
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

        private ArrayList<String> jsonParser(String s) {
            ArrayList<String> movies = new ArrayList<>();
            try {

                JSONObject mObject = new JSONObject(s);
                JSONArray resultsArray = mObject.getJSONArray("results");
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject indexObject = resultsArray.getJSONObject(i);
                    String indexMovie = new String("https://www.youtube.com/watch?v=" + indexObject.getString("key"));
                    movies.add(indexMovie); // Add each item to the list
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return movies;
        }

    }

    public class SyncR extends AsyncTask<URL, Void, ArrayList<String>> {


        @Override
        protected ArrayList<String> doInBackground(URL... paramas) {
//            URL url = createURL(paramas[0]);
            String json = URLResult(paramas[0]);
            ArrayList<String> movies = jsonParser(json);

            return movies;
        }

        @Override
        protected void onPostExecute(ArrayList<String> list) {
            super.onPostExecute(list);
            rList = list;
            updateR(list);
        }

        private String URLResult(URL webAddress) {
            InputStream inputStream = null;
            String json = null;
            try {
                if (webAddress != null) {
                    HttpURLConnection connection = (HttpURLConnection) webAddress.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    inputStream = connection.getInputStream();
                    json = readFromStream(inputStream);
                    connection.disconnect();
                    inputStream.close();
                }
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

        private ArrayList<String> jsonParser(String s) {
            ArrayList<String> movies = new ArrayList<>();
            try {

                JSONObject mObject = new JSONObject(s);
                JSONArray resultsArray = mObject.getJSONArray("results");
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject indexObject = resultsArray.getJSONObject(i);
                    String indexMovie = new String(indexObject.getString("content"));
                    movies.add(indexMovie); // Add each item to the list
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return movies;
        }

    }
}



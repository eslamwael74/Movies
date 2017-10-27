package com.example.eslam.finalmovies;


import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by eslam on 10/17/2016.
 */

public class Movie implements Serializable {
    private int ID;
    private String title;
    private String date;
    private String picture;
    private int vote;
    private double popularity;
    private String description;
    private boolean isFav;
    private int IDI;


    public Movie(int ID, String date, String description, String picture, double popularity, String title, int vote, boolean isFav, int IDI) {
        this.date = date;
        this.isFav = isFav;
        this.IDI = IDI;
        this.description = description;
        this.picture = picture;
        this.popularity = popularity;
        this.title = title;
        this.vote = vote;
        this.ID = ID;
    }

    public Movie() {
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFav() {
        return isFav;
    }

    public boolean isFavD() {
        ArrayList<Movie> movies = db.getInstance().getFav();
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getPicture().equals(getPicture()))
                return true;
            setFav(true);
        }
        return false;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }



    public int getIDI() {
        return IDI;
    }

    public void setIDI(int IDI) {
        this.IDI = IDI;
    }


}

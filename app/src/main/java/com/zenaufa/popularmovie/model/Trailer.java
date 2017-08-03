package com.zenaufa.popularmovie.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Zen Aufa on 8/4/2017.
 */

public class Trailer {

    @SerializedName("id")
private int id;
    @SerializedName("Results")
    private ArrayList<TrailerResults> results;

    public Trailer() {
        // Constructor
    }

    public int getId() {
        return id;
    }

    public ArrayList<TrailerResults> getResults() {
        return results;
    }

    public void setResults(ArrayList<TrailerResults> results) {
        this.results = results;
    }
}
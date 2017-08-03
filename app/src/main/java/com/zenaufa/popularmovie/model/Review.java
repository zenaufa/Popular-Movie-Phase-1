package com.zenaufa.popularmovie.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Zen Aufa on 8/4/2017.
 */

public class Review {
        @SerializedName("id")
        private int id;
        @SerializedName("page")
        private int page;
        @SerializedName("Results")
        private ArrayList<ReviewResults> results;
        @SerializedName("total_pages")
        private int totalPages;
        @SerializedName("total_results")
        private int totalResults;

        public Review() {
            // Constructor
        }

        public int getId() {
            return id;
        }

        public int getPage() {
            return page;
        }

        public ArrayList<ReviewResults> getResults() {
            return results;
        }

        public void setResults(ArrayList<ReviewResults> results) {
            this.results = results;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public int getTotalResults() {
            return totalResults;
        }
}

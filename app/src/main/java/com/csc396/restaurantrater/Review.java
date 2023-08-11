package com.csc396.restaurantrater;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class Review implements Serializable {

    private String name;
    private String date;
    private String time;
    private String meal;
    private int rating;
    private boolean isFavorite;

    public Review(String name, String date, String time, String meal, int rating, boolean isFavorite) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.meal = meal;
        this.rating = rating;
        this.isFavorite = isFavorite;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getMeal() {
        return meal;
    }

    public int getRating() {
        return rating;
    }

    public boolean isFavorite() {
        return isFavorite;
    }


}

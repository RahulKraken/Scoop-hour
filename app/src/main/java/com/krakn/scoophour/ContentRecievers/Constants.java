package com.krakn.scoophour.ContentRecievers;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    // BASE URL FOR THE API
    public final String BASE_URL = "https://newsapi.org/v2/";
    // API KEY
    public final String API_KEY = "&apiKey=01fe6663a4aa40ed9dd0f348b967bf69";

    // List of the categories showed in the view pager
    public List<String> categoryList = new ArrayList<>();

    public Constants() {
        // All the categories : general business entertainment sports technology science health
        categoryList.add("general");
        categoryList.add("business");
        categoryList.add("entertainment");
        categoryList.add("sports");
        categoryList.add("technology");
        categoryList.add("science");
        categoryList.add("health");
    }
}

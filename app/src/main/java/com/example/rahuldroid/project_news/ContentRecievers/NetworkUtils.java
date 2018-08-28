package com.example.rahuldroid.project_news.ContentRecievers;

import android.support.annotation.Nullable;
import android.util.Log;

import com.example.rahuldroid.project_news.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkUtils {

    // This java class contains methods for network interactions and getting the content from web using NewsApi free API.

    // This variable stores the code of the active fragment.
    private int FRAG_CODE;
    private String keyword;

    /**
     * Returns the article contents from the JSONObject received from the newApi servers corresponding to the active fragment index
     * @param fragmentIndex
     * @return data
     */
    public ArrayList<DataModel> returnContent(int fragmentIndex, @Nullable String keyword) {

        ArrayList<DataModel> data = new ArrayList<>();

        try {

            // Setting the FRAG_CODE to the received fragmentIndex.
            FRAG_CODE = fragmentIndex;

            if (keyword != null && fragmentIndex == 15) {
                this.keyword = keyword;
            }

            JSONObject jsonObject = getJsonObject();

            // Got the Json Object and now we are going to make Json Array and then extract the information out of it and then
            // populate our data model with it.
            JSONArray jsonArray = new JSONArray(jsonObject.getString("articles"));

            // Articles are in the form of an array in the json file but each individual article is a json object so now we
            // need to take individual object of the article.
            for (int i = 0; i < jsonArray.length(); i++) {

                // Individual article information stored in a data model object
                DataModel model = new DataModel();

                JSONObject articleObject = jsonArray.getJSONObject(i);
                JSONObject source = articleObject.getJSONObject("source");

                model.setTitle(articleObject.getString("title"));
                model.setDescription(articleObject.getString("description"));
                model.setArticleUrl(articleObject.getString("url"));
                model.setImageUrl(articleObject.getString("urlToImage"));
                model.setSourceName(source.getString("name"));

                data.add(model);

                Log.d("ARTICLES", model.getTitle() + " " + model.getDescription() + " " + model.getArticleUrl());
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * returns a JSON object using the newApi URL
     * @return JSONObject
     */
    private JSONObject getJsonObject() {
        try {

            // Converts the rawJson from string format to Json Object.
            return new JSONObject(getRawJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *This method returns the raw json file in string form from a link, here from the newsApi link
     */
    private String getRawJson() {

        UrlCreator urlCreator = new UrlCreator();
        String url;

        if (FRAG_CODE != 15) {
            url = urlCreator.returnUrl(FRAG_CODE);
        } else {
            url = urlCreator.createUrlForSearch(keyword);
        }
        String rawJson = "";

        URL activeUrl;

        try {

            activeUrl = new URL(url);

            HttpURLConnection httpURLConnection = (HttpURLConnection) activeUrl.openConnection();
            InputStream in = httpURLConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);

            int i = reader.read();

            while (i != -1) {
                char current = (char) i;
                rawJson += current;
                i = reader.read();
            }

            Log.d("RAW JSON", rawJson);

            return rawJson;

        } catch (IOException e) {

            e.printStackTrace();
            Log.d("ERROR", "Error getting json.");

            return String.valueOf(R.string.error_message_json_recieving);
        }
    }
}

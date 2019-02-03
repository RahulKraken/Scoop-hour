package com.krakn.scoophour.ContentRecievers;

import android.util.Log;

public class UrlCreator {

    private static final String TAG = "UrlCreator";

    private Constants constants;
    private String BASE_URL;

    /**
     * returns the base url on which the NetworkUtils class method work to provide the content from various categories.
     * @param index
     * @return url
     */
    public String returnUrl(int index) {
        // Returns the base url of the news Api API
        constants = new Constants();
        BASE_URL = constants.BASE_URL;
        String url = "";

        // All the categories : general business entertainment sports technology science health
        switch (index) {
            case 0: url = makeUrl("general");           break;
            case 1: url = makeUrl("business");          break;
            case 2: url = makeUrl("entertainment");     break;
            case 3: url = makeUrl("sports");            break;
            case 4: url = makeUrl("technology");        break;
            case 5: url = makeUrl("science");           break;
            case 6: url = makeUrl("health");            break;
        }
        return url;
    }

    // This method makes the url using the parameter passed
    private String makeUrl(String string) {
        String bUrl = modifyUrl(BASE_URL);
        return bUrl.concat(string).concat(constants.API_KEY);
    }

    private String modifyUrl(String string) {
        return string.concat("top-headlines?country=in&category=");
    }

    /**
     * Returns url for search action on @param 'keyword'
     * @param keyword
     * @return url
     */
    public String createUrlForSearch(String keyword) {

        // https://newsapi.org/v2/everything?q=narendra+modi&pageSize=50
        // &language=en&from=2018-08-25&to=2018-08-26&sortBy=relevency&apiKey=01fe6663a4aa40ed9dd0f348b967bf69

        // Returns the base url of the news Api API
        constants = new Constants();
        BASE_URL = constants.BASE_URL;

        Log.d(TAG, "createUrlForSearch: invoked successfully");

        keyword = prepareKeyword(keyword);

        Log.d(TAG, "createUrlForSearch: prepared keyword - " + keyword);

        String url = BASE_URL.concat("everything?q=")
                .concat(keyword)
                .concat("&pageSize=50")
                .concat("&language=en")
                .concat("&sortBy=relevency")
                .concat(constants.API_KEY);

        Log.d(TAG, "createUrlForSearch: " + url);
        return url;
    }

    private String prepareKeyword(String keyword) {
        return keyword.replace(" ", "+");
    }
}

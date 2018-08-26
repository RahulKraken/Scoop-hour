package com.example.rahuldroid.project_news.ContentRecievers;

public class UrlCreator {

    private Constants constants;
    private String BASE_URL;

    // This method returns the base url on which the NetworkUtils class method work to provide the content from various categories.
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

    // BASE URL FOR THE API
//    public final String BASE_URL = "https://newsapi.org/v2/";
    // API KEY
//    public final String API_KEY = "&apiKey=01fe6663a4aa40ed9dd0f348b967bf69";

    private String modifyUrl(String string) {
        return string.concat("top-headlines?country=in&category=");
    }
}

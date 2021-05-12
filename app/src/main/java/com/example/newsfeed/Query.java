package com.example.newsfeed;

import android.text.TextUtils;
import android.util.Log;

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
import java.util.List;

public final class Query {

    private static final String LOG_TAG = Query.class.getSimpleName();

    private Query() {
    }

    /*Query dataset and return a list of objects*/
    public static List<News> fetchNews(String requestUrl) {
        URL url = createUrl(requestUrl);

        // Perform HTTP request and receive a JSON response
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem with the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response
        List<News> news = extractFeatureFromJson(jsonResponse);

        return news;
    }

    /*Return URL object from string URL*/
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building URL ", e);
        }
        return url;
    }

    /*Convert inputstream to String */
    private static String readFromStream(InputStream inputStream) throws IOException {
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

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(13000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            /*If the request was successful
             then read the input stream and parse the response*/
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the News JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static List<News> extractFeatureFromJson(String NewsJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(NewsJSON)) {
            return null;
        }

        List<News> mNews = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(NewsJSON);

            JSONObject NewsArray = baseJsonResponse.getJSONObject("response");

            JSONArray results = NewsArray.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {

                JSONObject currentNews = results.getJSONObject(i);

                String Section = currentNews.getString("sectionName");

                String Title = currentNews.getString("webTitle");

                String Date = currentNews.getString("webPublicationDate");

                String url = currentNews.getString("webUrl");

                String Pillar = currentNews.getString("pillarName");
                
                String Author = null;
                JSONArray tags = new JSONArray(results.getJSONObject(i).getString("tags"));
                for (int j = 0; j < tags.length(); j++) {
                    Author = tags.getJSONObject(j).getString("webTitle");
                }
                News news = new News(Section, Title, Date, url, Pillar,Author);

                mNews.add(news);
            }

        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the News JSON results", e);
        }

        return mNews;
    }
}

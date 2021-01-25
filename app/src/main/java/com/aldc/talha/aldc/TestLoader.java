package com.aldc.talha.aldc;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by HP-NPC on 05/08/2017.
 */

public class TestLoader extends AsyncTaskLoader<String> {
    String json = "";
    public URL url = null;
    public String MCQ_URL = "https://dummyes.000webhostapp.com/mcq.json";
    public TestLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            url = new URL(MCQ_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        try {
            json = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return json;
    }


    private static String makeHttpRequest(URL url) throws IOException{
        String json = "";

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
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
                json = output.toString();
            } else {
                Log.e("VUA", "Error response code: " + urlConnection.getResponseCode());
            }

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return json;
    }

}

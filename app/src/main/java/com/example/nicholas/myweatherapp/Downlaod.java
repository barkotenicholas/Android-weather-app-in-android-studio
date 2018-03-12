package com.example.nicholas.myweatherapp;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nicholas on 3/12/18.
 */

public class Downlaod extends AsyncTask<String,Void,String> {


 @Override
 protected String doInBackground(String... urls ) {

     String result = null ;
     URL url;
     HttpURLConnection urlConnection;
     try {
         url = new URL(urls[0]);
         urlConnection=(HttpURLConnection) url.openConnection();
         InputStream in = urlConnection.getInputStream();
         InputStreamReader read = new InputStreamReader(in);
         int data = read.read();

         while (data != -1){
             char current = (char) data;
             result+=current;
             data=read.read();
         }

         return  result;

     } catch (MalformedURLException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     }


     return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            String weatherinfo =jsonObject.getString("weather");
            JSONObject weatherdata = new JSONObject(jsonObject.getString("main"));
            double tempInt = Double.parseDouble(weatherdata.getString("temp"));
            int tempin = (int) (tempInt*1.8-459.67);
            String placename = jsonObject.getString("name");

            MainActivity.name.setText(placename);
            MainActivity.temp.setText(tempin);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

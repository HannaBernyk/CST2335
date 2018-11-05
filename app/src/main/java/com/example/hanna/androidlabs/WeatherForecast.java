package com.example.hanna.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends Activity {
    protected static final String ACTIVITY_NAME = "WeatherForecast222";
    private ProgressBar progressBar;
    private ImageView imageView;
    private TextView windSpeedTextView;
    private TextView minTemperatureTextView;
    private TextView maxTemperatureTextView;
    private TextView currentTemperatureTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        Log.i(ACTIVITY_NAME, "In onCreate");

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        imageView = findViewById(R.id.currentWeather);
        windSpeedTextView = findViewById(R.id.windSpeed);
        minTemperatureTextView = findViewById(R.id.minTemperature);
        maxTemperatureTextView = findViewById(R.id.maxTemperature);
        currentTemperatureTextView = findViewById(R.id.currentTemperature);

        new ForecastQuery().execute();
    }


    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String windSpeed = "";
        private String minTemperature = "";
        private String maxTemperature = "";
        private String currentTemperature = "";
        private Bitmap currentWeather;
        private String weatherURL = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(weatherURL);
            } catch (MalformedURLException e) {
                Log.e(ACTIVITY_NAME, e.getMessage());
            }
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();

                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(conn.getInputStream(), null);
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if(parser.getName() != null){

                        switch (parser.getName()) {
                            case "temperature":
                                if(parser.getAttributeValue(null, "value") != null) {
                                    currentTemperature = parser.getAttributeValue(null, "value");
                                    publishProgress(50);
                                }

                                if(parser.getAttributeValue(null, "min") != null) {
                                    minTemperature = parser.getAttributeValue(null, "min");
                                    publishProgress(75);
                                }

                                if(parser.getAttributeValue(null, "max") != null){
                                    maxTemperature = parser.getAttributeValue(null, "max");
                                }
                                break;
                            case "speed":
                                if(parser.getAttributeValue(null, "value") != null){
                                    windSpeed = parser.getAttributeValue(null, "value");
                                    publishProgress(25);
                                }
                                break;
                            case "weather":
                                String iconName = parser.getAttributeValue(null, "icon");
                                if(iconName != null){
                                    FileInputStream fis = null;
                                    String imagefile = iconName + ".png";
                                if(fileExistance(imagefile)){
                                    try {
                                        fis = openFileInput(imagefile);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    currentWeather = BitmapFactory.decodeStream(fis);
                                    Log.i(ACTIVITY_NAME, "File with name " + imagefile + " was found locally.");
                                } else {
                                    currentWeather = getAndSaveImage("http://openweathermap.org/img/w/" + iconName + ".png", iconName);
                                    Log.i(ACTIVITY_NAME, "We need to download the " + imagefile);
                                }
                                publishProgress(100);
                                }
                                break;
                        }
                    }
                    eventType = parser.next();
                }
            } catch (IOException | XmlPullParserException e) {
                Log.e(ACTIVITY_NAME, e.getMessage());
            }
            return null;
        }

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }


        private Bitmap getAndSaveImage(String imageURL, String iconName) {
            Bitmap image = HttpUtils.getImage(imageURL);
            FileOutputStream outputStream = null;
            try {
                outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                Log.e(ACTIVITY_NAME, e.getMessage());
            }
            return image;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            imageView.setImageBitmap(currentWeather);
            maxTemperatureTextView.append(maxTemperature);
            minTemperatureTextView.append(minTemperature);
            currentTemperatureTextView.append(currentTemperature);
            windSpeedTextView.append(windSpeed);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }


}

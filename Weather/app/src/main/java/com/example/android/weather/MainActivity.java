package com.example.android.weather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MainActivity extends AppCompatActivity {

    String url = "https://api.darksky.net/forecast/YOUR_API_KEY/12.988895,74.80584";
    Gson gson;
    RequestQueue queue;

    TextView textView1;
    TextView textView2;
    TextView textView3;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        queue = Volley.newRequestQueue(MainActivity.this);

        textView1 = findViewById(R.id.weatherSummary);
        textView2 = findViewById(R.id.weatherTemperature);
        textView3 = findViewById(R.id.weatherHumidity);
        imageView = findViewById(R.id.weatherIcon);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String responseFinal) {
                        Log.i("tag", responseFinal);
                        JsonElement jelement = new JsonParser().parse(responseFinal);
                        JsonObject jobject = jelement.getAsJsonObject();
                        jobject = jobject.getAsJsonObject("currently");

                        String summary = jobject.get("summary").getAsString();
                        String icon = jobject.get("icon").getAsString();
                        String temperature = jobject.get("temperature").getAsString();
                        String humidity = jobject.get("humidity").getAsString();
                        Double Fahrenheit = Double.parseDouble(temperature);
                        Double celcius = (Fahrenheit - 32) * 0.5556;
                        temperature = celcius.toString();
                        temperature = temperature.substring(0,5);
                        Log.i("result", summary);
//                        summary = '"' + summary + '"';
                        textView1.setText('"' + summary + '"');
                        textView3.setText("Temperature - " + temperature);
                        textView2.setText("Humidity - " + humidity);
//                        Toast.makeText(MainActivity.this, responseFinal, Toast.LENGTH_LONG).show();


                        setIcon(icon);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                        response.setText("That didn't work!");
                Log.e("tag", error.toString());
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

//        setIcon("night-cloud-wind");

    }


    public void setIcon(String iconName) {
        String weatherIcon = iconName;
        CharSequence weatherType1 = "day";
        CharSequence weatherType2 = "night";
        CharSequence weatherType3 = "sunny";
        CharSequence weatherType4 = "rain";
        CharSequence weatherType5 = "cloud";
        CharSequence weatherType6 = "shower";
        CharSequence weatherType7 = "storm";
        CharSequence weatherType8 = "snow";
        CharSequence weatherType9 = "thunder";
        CharSequence weatherType10 = "sandstorm";
        CharSequence weatherType11 = "fog";
        CharSequence weatherType12 = "wind";
        CharSequence weatherType13 = "hail";
        CharSequence weatherType14 = "thunder";


        boolean bool1, bool2, bool3;


        if ((weatherIcon.contains(weatherType1)) && (weatherIcon.contains(weatherType3))) {
            imageView.setImageResource(R.drawable.day_normal);
        } else if ((weatherIcon.contains(weatherType1)) && (weatherIcon.contains(weatherType10))) {
            imageView.setImageResource(R.drawable.day_sandstorm);
        } else if ((weatherIcon.contains(weatherType1)) && ((weatherIcon.contains(weatherType7)) || (weatherIcon.contains(weatherType9)) || (weatherIcon.contains(weatherType14)))) {
            imageView.setImageResource(R.drawable.day_storm);
        } else if ((weatherIcon.contains(weatherType1)) && ((weatherIcon.contains(weatherType4)) || (weatherIcon.contains(weatherType6)))) {
            imageView.setImageResource(R.drawable.day_rain);
        } else if ((weatherIcon.contains(weatherType1)) && ((weatherIcon.contains(weatherType8)) || (weatherIcon.contains(weatherType13)))) {
            imageView.setImageResource(R.drawable.snow);
        } else if ((weatherIcon.contains(weatherType1)) && (weatherIcon.contains(weatherType12))) {
            imageView.setImageResource(R.drawable.day_wind);
        } else if ((weatherIcon.contains(weatherType1)) && (weatherIcon.contains(weatherType11))) {
            imageView.setImageResource(R.drawable.day_fog);
        } else if ((weatherIcon.contains(weatherType1)) && (weatherIcon.contains(weatherType5))) {
            imageView.setImageResource(R.drawable.day_cloud);
        } else if (weatherIcon.contains(weatherType1)) {
            imageView.setImageResource(R.drawable.day);
        } else if ((weatherIcon.contains(weatherType2)) && (weatherIcon.contains(weatherType10))) {
            imageView.setImageResource(R.drawable.night_sandstorm);
        } else if ((weatherIcon.contains(weatherType2)) && ((weatherIcon.contains(weatherType7)) || (weatherIcon.contains(weatherType9)) || (weatherIcon.contains(weatherType14)))) {
            imageView.setImageResource(R.drawable.night_storm);
        } else if ((weatherIcon.contains(weatherType2)) && ((weatherIcon.contains(weatherType4)) || (weatherIcon.contains(weatherType6)))) {
            imageView.setImageResource(R.drawable.night_rain);
        } else if ((weatherIcon.contains(weatherType2)) && ((weatherIcon.contains(weatherType8)) || (weatherIcon.contains(weatherType13)))) {
            imageView.setImageResource(R.drawable.snow);
        } else if ((weatherIcon.contains(weatherType2)) &&  (weatherIcon.contains(weatherType12))) {
            imageView.setImageResource(R.drawable.night_wind);
        } else if ((weatherIcon.contains(weatherType2)) && (weatherIcon.contains(weatherType11))) {
            imageView.setImageResource(R.drawable.night_fog);
        } else if ((weatherIcon.contains(weatherType2)) && (weatherIcon.contains(weatherType5))) {
            imageView.setImageResource(R.drawable.night_cloud);
        } else if (weatherIcon.contains(weatherType2)) {
            imageView.setImageResource(R.drawable.night);
        } else {
            imageView.setImageResource(R.drawable.default_weather);
        }


    }
}

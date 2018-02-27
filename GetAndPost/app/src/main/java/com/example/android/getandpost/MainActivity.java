package com.example.android.getandpost;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {

    String domain = "http://127.0.0.1:8000/data/";
    String domain1 = "http://10.50.21.189";
    String domain2 = "http://10.50.21.64:8000/user/test";

    RequestQueue queue;
    Gson gson;
    Button button;

    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        queue = Volley.newRequestQueue(MainActivity.this);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(onClickListener);


    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, domain2,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String responseFinal) {
                            Log.i("tag", responseFinal);
                           // Toast.makeText(MainActivity.this, responseFinal, Toast.LENGTH_SHORT).show();
                            textView = (TextView) findViewById(R.id.text);
                            textView.setText(responseFinal);

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

        }



    };
}


//import android.content.SharedPreferences;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        final RequestQueue queue = Volley.newRequestQueue(this);
//
//        Button b = (Button) findViewById(R.id.button);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://10.50.21.189:8000/data/",
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String responseFinal) {
//                                // Display the response string.
////                        response.setText(responseFinal);
//                                Log.i("tag2", responseFinal);
//                                Toast.makeText(MainActivity.this, responseFinal, Toast.LENGTH_LONG).show();
////                                response.setText(CSRFToken);
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
////                        response.setText("That didn't work!");
//                        Log.e("tag3", error.toString());
//                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
//                    }
//                });
//                // Add the request to the RequestQueue.
//                queue.add(stringRequest);
//            }
//        });
//
//    }
//}
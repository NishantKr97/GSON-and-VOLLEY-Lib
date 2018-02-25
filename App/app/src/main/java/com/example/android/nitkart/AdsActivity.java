package com.example.android.nitkart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class AdsActivity extends AppCompatActivity {

    ListView listView;

    final String[] userAds = {"apple", "banana", "cat", "dog", "egg", "fish", "gun", "hello", "india"};

    TextView userProfileActivityButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);




        // ListView settings
        listView = findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);


        ///temp button to show intent of the profile
        userProfileActivityButton = findViewById(R.id.userProfileActivityButton);
        userProfileActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdsActivity.this, UserDetails.class));
            }
        });


    }





    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return userAds.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.add_template, null);
            ImageView imageView = (ImageView) findViewById(R.id.image);


            //Set the image in future
            //imageView.setImageResource(image[]);
            TextView tv1 = (TextView) view.findViewById(R.id.textview11);
            TextView tv2 = (TextView) view.findViewById(R.id.textview21);



            tv1.setText(userAds[i]);
            tv2.setText(userAds[i]);

            return view;
        }
    }


}

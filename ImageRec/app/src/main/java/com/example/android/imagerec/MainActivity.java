package com.example.android.imagerec;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.SyncParams;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.AnalysisResult;
import com.microsoft.projectoxford.vision.contract.Caption;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {

    public VisionServiceClient visionServiceClient = new VisionServiceRestClient("de3c08420cac46f8821905810beb9d32");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flower);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        TextView textView = (TextView) findViewById(R.id.txtDescription);
        Button btnProcess = (Button) findViewById(R.id.btnProcess);

        imageView.setImageBitmap(mBitmap);



        // Convert image to stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());


        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                @SuppressLint("StaticFieldLeak") final AsyncTask<InputStream,String, String> visionTask = new AsyncTask<InputStream, String, String>() {

                    ProgressDialog mDialog = new ProgressDialog(MainActivity.this);

                    @Override
                    protected String doInBackground(InputStream... params) {
                        try {
                            publishProgress("Recognizing....");
                            String feature = "Description";
                            String[] features ={feature};
                            String[] details = {};


                            AnalysisResult result = visionServiceClient.analyzeImage(params[0], features, details);
                            AnalysisResult just = result;
//                            Log.e("msg", just);
                            //Toast.makeText(MainActivity.this, (String)just, Toast.LENGTH_SHORT ).show();
                            String strResult = new Gson().toJson(result);
                            return strResult;
                        }catch (Exception e){
                            return e.getMessage();
                        }
                    }

                    @Override
                    protected void onPreExecute() {
                        mDialog.show();
                    }

                    @Override
                    protected void onPostExecute(String s) {
//                        mDialog.dismiss();
//
//                        AnalysisResult result = new Gson().fromJson(s, AnalysisResult.class);
//                        TextView textView = (TextView) findViewById(R.id.txtDescription);
//                        StringBuilder stringBuilder = new StringBuilder();
//                        for(Caption caption:result.description.captions)
//                        {
//                            stringBuilder.append(caption.text);
//                        }
//                        textView.setText(stringBuilder);

                        mDialog.dismiss();
                        StringBuilder stringBuilder = new StringBuilder();
                        TextView textView = findViewById(R.id.txtDescription);
                        try {
                            AnalysisResult result = new Gson().fromJson(s, AnalysisResult.class);
                            for (Caption caption: result.description.captions) {
                                stringBuilder.append(caption.text);
                            }
                        } catch (Exception e) {
                            stringBuilder.append(e.getCause());
                        }
                        textView.setText(stringBuilder);
                    }

                    @Override
                    protected void onProgressUpdate(String... values) {
                        mDialog.setMessage(values[0]);
                    }

                    @Override
                    protected void onCancelled(String s) {
                        super.onCancelled(s);
                    }


                };

                visionTask.execute(inputStream);
            }
        });



    }
}

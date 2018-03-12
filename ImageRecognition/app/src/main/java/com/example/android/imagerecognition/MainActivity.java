package com.example.android.imagerecognition;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.android.library.camera.CameraHelper;
import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;

import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private VisualRecognition vrClient;
    private CameraHelper helper;
    File photoFile = null;
    TextView detectedObjects;
    String api_key = "8779793ffd1b1fc758adb0c9bde783aa0bc478e3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize Visual Recognition client
        vrClient = new VisualRecognition(
                VisualRecognition.VERSION_DATE_2016_05_20,
                "8779793ffd1b1fc758adb0c9bde783aa0bc478e3"
        );

        // Initialize camera helper
        helper = new CameraHelper(this);




    }

    public void takePicture(View view) {
        helper.dispatchTakePictureIntent();
        detectedObjects =
                findViewById(R.id.detected_objects);
        detectedObjects.setText("");
    }


    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CameraHelper.REQUEST_IMAGE_CAPTURE) {
            // More code here

            final Bitmap photo = helper.getBitmap(resultCode);
            photoFile = helper.getFile(resultCode);

            ImageView preview = findViewById(R.id.preview);
            preview.setImageBitmap(photo);
        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

//                VisualRecognition service = new VisualRecognition(
//                        VisualRecognition.VERSION_DATE_2016_05_20
//                );
//                service.setApiKey("8779793ffd1b1fc758adb0c9bde783aa0bc478e3");
//
//                InputStream imagesStream = null;
//                try {
//                    imagesStream = new FileInputStream(photoFile);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
//                        .imagesFile(imagesStream)
//                        .imagesFilename("fruitbowl")
//                        .parameters("0.6")
//                        .build();
//                ClassifiedImages result = service.classify(classifyOptions).execute();
////                System.out.println(result);
//
//                findViewById(R.id.detected_objects);
//        detectedObjects.setText(result.toString());
//
//            }
//        });

                VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_19);
                service.setApiKey("<my api key here>");
                System.out.println("Classify an image");
                ClassifyImagesOptions options = new ClassifyImagesOptions.Builder()
                        .images(new File("src/test/resources/golf.png"))
                        .build();
                VisualClassification result = service.classify(options).execute();
                System.out.println(result);
            }



    }

}
//
//    VisualRecognition response =
//            vrClient.classify(
//                    new ClassifyImagesOptions.Builder()
//                            .images(photoFile)
//                            .build()
//            ).execute();
//
//    // More code here
//
//
//    ImageClassification classification =
//            response.getImages().get(0);
//
//    VisualClassifier classifier =
//            classification.getClassifiers().get(0);
//
//    final StringBuffer output = new StringBuffer();
//                for(VisualClassifier.VisualClass object: classifier.getClasses()) {
//                        if(object.getScore() > 0.35f)
//                        output.append("<")
//                        .append(object.getName())
//                        .append("> ");
//                        }
//
//                        runOnUiThread(new Runnable() {
//@Override
//public void run() {
//        detectedObjects =
//        findViewById(R.id.detected_objects);
//        detectedObjects.setText(output);
//        }
//        });
//
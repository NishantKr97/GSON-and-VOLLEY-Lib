package com.example.android.imageurl;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.android.imageurl.R.id.imgView;
//import static com.example.android.imageurl.MainActivity.SaveImage.imageDownload;

public class MainActivity extends AppCompatActivity {

    Context context;

    private EditText editText;
    private ImageView imageView;
    private TextView textView;

    String src = "http://www.qygjxz.com/data/out/36/4478898-boy-images.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        imageView = (ImageView) findViewById(R.id.imgView);
        textView = (TextView) findViewById(R.id.text);

        // Create a file in the Internal Storage
        String fileName = "MyFile";
        String content = "hello world";

        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.close();
            Log.d("File created", "File is created");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String rollString = editText.getText().toString().trim();
        //imageView.setImageBitmap(getBitmapFromURL(src));

        //Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(imageView);

        //Picasso.with(this).load("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").into(picassoImageTarget(getApplicationContext(), "imageDir", "my_image.jpeg"));
        // Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(picassoImageTarget(getApplicationContext(), "imageDir", "my_image.jpeg"));

        //File folder = getBaseContext().getDir("users", Context.MODE_PRIVATE);

        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        Boolean isSDSupportedDevice = Environment.isExternalStorageRemovable();

        if (isSDPresent && isSDSupportedDevice) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File myImageFile = new File(directory, "my_image.jpeg");
        Picasso.with(this).load(myImageFile).into(imageView);
        } else {
            try {
                File mydir = getBaseContext().getDir("ABCDEFGH", Context.MODE_PRIVATE); //Creating an internal dir;
                File fileWithinMyDir = new File(mydir, "ABCDEFGH"); //Getting a file within the dir.
                Picasso.with(this).load(fileWithinMyDir).into(imageView);
                //FileOutputStream out = new FileOutputStream(fileWithinMyDir); //Use the stream as usual to write into the file.

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String folderName = "MyNewFolder";

        File f = new File(Environment.getExternalStorageDirectory(), folderName);
        if(!f.exists())
        {
            f.mkdir();
        }


        SaveImage saveImage = new SaveImage();
        saveImage.imageDownload(this, src);

        boolean success = true;
        if(!folder.exists())
            success = folder.mkdirs();
    }

    private Target picassoImageTarget(Context context, final String imageDir, final String imageName) {
        Log.d("picassoImageTarget", " picassoImageTarget");
        ContextWrapper cw = new ContextWrapper(context);
        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE); // path to /data/data/yourapp/app_imageDir
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final File myImageFile = new File(directory, imageName); // Create image file
                        FileOutputStream fos = null;
                        Log.d("Image file created", "Image file created");


                        //if (isSDSupportedDevice && isSDPresent) {
                        // yes SD-card is present
                        try {
                            fos = new FileOutputStream(myImageFile);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            Log.d("AAAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAAAAA");
                        } catch (IOException e) {
                            Log.d("in catch", "in catch");
                            e.printStackTrace();
                        }
                          finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("image", "image saved to >>>" + myImageFile.getAbsolutePath());
                        textView.setText("image saved to");
                        textView.append(myImageFile.getAbsolutePath());
                        //}
                        } else {
                            // Sorry
                            try
                            {
                                File mydir = getBaseContext().getDir("ABCDEFGH", Context.MODE_PRIVATE); //Creating an internal dir;
                                File fileWithinMyDir = new File(mydir, "myfile"); //Getting a file within the dir.
                                //FileOutputStream out = new FileOutputStream(fileWithinMyDir); //Use the stream as usual to write into the file.

                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }


                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {
                }
            }
        };
    }
}


    class SaveImage implements Target{

        //save image
        public void imageDownload(Context ctx, String url){
            Picasso.with(ctx)
                    .load("http://i.imgur.com/DvpvklR.png")
                    .into(getTarget(url));
        }

        //target to save
        private Target getTarget(final String url){
            Target target = new Target(){

                @Override
                public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {

                            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + url);
                            try {
                                file.createNewFile();
                                FileOutputStream ostream = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                                ostream.flush();
                                ostream.close();
                            } catch (IOException e) {
                                Log.e("IOException", e.getLocalizedMessage());
                            }
                        }
                    }).start();

                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            return target;
        }
        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {

        }

    }

target to save
    private static Target getTarget(final String url){
        Target target = new Target(){

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + url);
                        try {
                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                            ostream.flush();
                            ostream.close();
                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());
                        }
                    }
                }).start();

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }

    public void callImage(View view)
    {
        try
        {
            URL url = new URL("http://www.qygjxz.com/data/out/36/4478898-boy-images.jpg");
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imageView.setImageBitmap(bmp);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }

    private class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            this.imageView.setImageBitmap(result);
        }

    }
}
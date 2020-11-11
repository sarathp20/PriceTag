package com.example.pricetag;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pricetag.classifier.ImageClassifier;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class camPage extends AppCompatActivity {
    String productName;
    Button retake;
    ProgressDialog prodiag;
    ArrayList<String> p_name = new ArrayList<>();
    ArrayList<String> p_price = new ArrayList<>();
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1000;
    private static final int CAMERA_REQEUST_CODE = 10001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);
        initializeUIElements();
        retake=findViewById(R.id.startCamera2);
        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeUIElements();
            }
        });

    }
    private void initializeUIElements() {

        try {
            if (hasPermission()) {
                openCamera();
            } else {
                requestPermission();
            }
        }catch (Exception e){
            System.out.println(e);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        ImageView imageView;

        imageView = findViewById(R.id.iv_capture);
        TextView text = findViewById(R.id.detectedImage);
        if (requestCode == CAMERA_REQEUST_CODE) {

            Bitmap photo = (Bitmap) Objects.requireNonNull(Objects.requireNonNull(data).getExtras()).get("data");

            imageView.setImageBitmap(photo);


            ImageClassifier imageClassifier = null;
            try {
                imageClassifier = new ImageClassifier(this);
            } catch (IOException e) {
               System.out.println("error in object creation of classifier  "+e);
            }
            List<ImageClassifier.Recognition> predicitons = imageClassifier.recognizeImage(
                    photo, 0);
            final List<String> predicitonsList = new ArrayList<>();
            for (ImageClassifier.Recognition recog : predicitons) {
                predicitonsList.add(recog.getName());
            }
            productName=predicitonsList.get(0);
            text.setText(predicitonsList.get(0));
            showDetail();



        }
            super.onActivityResult(requestCode, resultCode, data);
        }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (hasAllPermissions(grantResults)) {
                openCamera();
            } else {
                requestPermission();
            }
        }
    }

    public void showDetail() {


        GetData data = new GetData();
        data.execute();
    }

    public void putData(){
        String name;
        String price;
        String imgurl;
        TextView det=findViewById(R.id.details);
        for (int i = 0; i < 3; i++) {
            name =(String) p_name.get(i);
            price =(String) p_price.get(i);
            det.setText(name+" "+price);

            /*imgurl =(String) p_image.get(i);
            try {
                InputStream is = (InputStream) new URL(imgurl).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                }
            catch (Exception e){
                text.setText(text.getText()+"Couldnt load image\n");
            }*/
        }

    }
    @SuppressLint("StaticFieldLeak")
    private class GetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            prodiag = new ProgressDialog(camPage.this);
            prodiag.setMessage("loading");
            prodiag.setIndeterminate(false);
            prodiag.show();
            //Toast.makeText(getApplicationContext(), "Best price you will get on Flipkart !!", Toast.LENGTH_LONG).show();
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            String[] productSplit = productName.split(" ");
            String productString = new String();
            int i=0;
            for (String a : productSplit){
                if(i==0){
                    productString=a;
                    i++;
                }
                else
                    productString=productString+"%20"+a;
            }
            String url = "https://www.flipkart.com/search?q=" + productString + "&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off";
            Document document;
            try {
                document = Jsoup.connect(url).get();
                Elements name = document.select("._3wU53n"); //get name
                Elements price = document.select("._1vC4OE._2rQ-NK"); //Get price
                //Elements images = document.select("_3BTv9X");
                //Elements imageclass = document.select("_3BTv9X");
                //Elements images = imageclass.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
                Elements name1 = document.select("._2cLu-l"); //get name
                //for (Element image : images) {
                //text.setText(text.getText()+image.attr("src"));
                //p_image.add((String)image.attr("src"));}
                Elements price1 = document.select("._1vC4OE"); //Get priceu
                for (i = 0; i < price.size() && i < name.size(); i++) {
                    p_name.add(name.get(i).text());
                    p_price.add(price.get(i).text());
                    // p_image.add(images.get(i).attr("src"));
                }
                for (i = 0; i < price1.size() && i < name1.size(); i++) {
                    p_name.add(name1.get(i).text());
                    p_price.add(price1.get(i).text());
                }
            } catch (Exception e) {
             System.out.println("nothing loaded\n");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            prodiag.dismiss();
            super.onPostExecute(aVoid);
            putData();
        }
    }


    private boolean hasAllPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED)
                return false;
        }
        return true;
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                Toast.makeText(this, "Camera Permission Required", Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);

        startActivityForResult(cameraIntent, CAMERA_REQEUST_CODE);
    }
    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }






}

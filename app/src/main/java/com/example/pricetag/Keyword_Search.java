package com.example.pricetag;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Locale;

public class Keyword_Search extends AppCompatActivity {
    Button search;
    TextView text;
    ProgressDialog prodiag;
    String productName;
    EditText item;
    ArrayList<String> p_name = new ArrayList<>();
    ArrayList<String> p_price = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.key);
        text=findViewById(R.id.keytext);
        item=findViewById(R.id.keyitem);
        item.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    showDetail();
                    return true;
                }
                return false;
            }
        });

    }
    public void getSpeechInput(View view){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,10);
        }
        else{
            Toast.makeText(this,"This feature is not supported in your device", Toast.LENGTH_SHORT).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch(requestCode){
            case 10:
                if(resultCode == RESULT_OK && data!=null){
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    item.setText(result.get(0));
                    showDetail();
                }
                break;
        }
    }
    public void showDetail() {

        productName = String.valueOf(item.getText());
        GetData data = new GetData();
        data.execute();
    }
    public void putData(){
        String name;
        String price;
        String imgurl;
        for (int i = 0; i < p_price.size() && i < p_name.size(); i++) {
            name =(String) p_name.get(i);
            price =(String) p_price.get(i);

            text.setText(text.getText()+name+" "+price+"\n\n\n");
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
    public class GetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            prodiag = new ProgressDialog(Keyword_Search.this);
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
                Elements name = document.select("._4rR01T"); //get name
                Elements price = document.select("._30jeq3._1_WHN1"); //Get price
                //Elements images = document.select("_3BTv9X");
                //Elements imageclass = document.select("_3BTv9X");
                //Elements images = imageclass.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
                Elements name1 = document.select(".s1Q9rs"); //get name
                //for (Element image : images) {
                //text.setText(text.getText()+image.attr("src"));
                //p_image.add((String)image.attr("src"));}
                Elements price1 = document.select("._30jeq3"); //Get priceu
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
                text.setText(text.getText() + "couldnt load url");
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
}

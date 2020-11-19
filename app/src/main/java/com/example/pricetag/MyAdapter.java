package com.example.pricetag;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ItemDisplay extends AppCompatActivity {
    Button buynow;
    ProgressDialog prodiag;
    String url;
    String site;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        site = intent.getStringExtra("site");
        Log.d("my",url);
        Log.d("my",site);
        buynow = findViewById(R.id.buynow);
        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ItemDisplay.this, ItemBuy.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
        ItemDisplay.GetData data = new ItemDisplay.GetData();
        data.execute();
    }
    public void putData(){

    }
    private class GetData extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            prodiag = new ProgressDialog(ItemDisplay.this);
            prodiag.setMessage("loading");
            prodiag.setIndeterminate(false);
            prodiag.show();
            //Toast.makeText(getApplicationContext(), "Best price you will get on Flipkart !!", Toast.LENGTH_LONG).show();
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            Document document;
            //   Document document2;
            if(site.equals("Flipkart")) {
                try {
                    document = Jsoup.connect(url).get();
                    Elements name = document.select(".B_NuCI");
                    Elements price = document.select("._30jeq3._16Jk6d");
                    Elements originalprice = document.select("._3I9_wc._2p6lqe");
                    Elements off =document.select("._3Ay6Sb._31Dcoz");
                    Elements offprice = off.select("span");
                    Elements rating = document.select("._3LWZlK");
                    Log.d("my",name.text());
                    Log.d("my",price.text());
                    Log.d("my",offprice.text());
                    Log.d("my",originalprice.text());
                    Log.d("my",rating.text().substring(0,3));
                    Elements availoffers = document.select(".XUp0WS");
                    Log.d("my","yes");
                    Elements availableoffers = availoffers.select("._16eBzU.col");
                    Log.d("my","yes1");
                    for(int i=0;i<availableoffers.size();i++){
                        Log.d("my","yes2");
                        Elements offerdetails = availableoffers.get(i).getElementsByTag("span");
                        for(int j=0;j<offerdetails.size();j++){
                            Log.d("my",offerdetails.get(j).text());
                        }

                    }
                    Elements images = document.select(".q6DClP");
                    for(int i=0;i<images.size();i++){
                        Log.d("my",images.get(i).attr("style"));
                    }

                } catch (Exception e) {
                    Log.d("my", "Connection Error");
                }

            }
            else if(site.equals("Snapdeal")){
                try {
                    document = Jsoup.connect(url).get();

                } catch (Exception e) {
                    Log.d("my", "Connection Error");
                }
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
package com.example.pricetag;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class whishList extends AppCompatActivity {
    private String productTitle;
    private String productPrice;
    private String cutoffPrice;
    private String productSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whish_list);
    }
}
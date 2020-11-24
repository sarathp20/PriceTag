package com.example.pricetag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class startPage extends AppCompatActivity {


    public static Button logbtn;
    public static Button userBtn;
    public static Button wishlistbtn;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        Button camStart=findViewById(R.id.startCamera);
        Button test=findViewById(R.id.keySearch);
        userBtn=findViewById(R.id.userbtn);
        logbtn=(Button)findViewById(R.id.logoutbtn);
        wishlistbtn=(Button)findViewById(R.id.wishbtn);
        if(MainActivity.hidebtn==1){
            logbtn.setVisibility(View.VISIBLE);
            userBtn.setVisibility(View.VISIBLE);
            wishlistbtn.setVisibility(View.VISIBLE);
        }
        else if(MainActivity.hidebtn==0){
            logbtn.setVisibility(View.GONE);
            userBtn.setVisibility(View.GONE);
            wishlistbtn.setVisibility(View.GONE);
        }
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent keySer=new Intent(startPage.this,Keyword_Search.class);
                startActivity(keySer);
            }
        });
        camStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openCam=new Intent(startPage.this,camPage.class);
                startActivity(openCam);
            }
        });
        wishlistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent wish=new Intent(startPage.this,wishList.class);
                startActivity(wish);
            }
        });
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent keySer=new Intent(startPage.this,userData.class);
                startActivity(keySer);
            }
        });
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
    }



}
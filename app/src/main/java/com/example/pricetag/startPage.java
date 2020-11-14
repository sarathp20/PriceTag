package com.example.pricetag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class startPage extends AppCompatActivity {
    public static Button logbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button camStart=findViewById(R.id.startCamera);
        Button test=findViewById(R.id.keySearch);
        logbtn=(Button)findViewById(R.id.logoutbtn);
        if(MainActivity.hidebtn==1){
            logbtn.setVisibility(View.VISIBLE);
        }
        else if(MainActivity.hidebtn==0){
            logbtn.setVisibility(View.GONE);
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

package com.example.pricetag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button camStart=findViewById(R.id.startCamera);
        Button test=findViewById(R.id.keySearch);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent keySer=new Intent(MainActivity.this,Keyword_Search.class);
                startActivity(keySer);
            }
        });
        camStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openCam=new Intent(MainActivity.this,camPage.class);
                startActivity(openCam);
            }
        });
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}
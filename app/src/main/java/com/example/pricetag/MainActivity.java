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
        Button reg = findViewById(R.id.register);
        Button log = findViewById(R.id.signin);
        Button wlog = findViewById(R.id.withoutsignin);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regOp = new Intent(MainActivity.this, Register.class);
                startActivity(regOp);
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logOp = new Intent(MainActivity.this, Login.class);
                startActivity(logOp);
            }
        });
        wlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logOp = new Intent(MainActivity.this, Keyword_Search.class);
                startActivity(logOp);
            }
        });
    }
}

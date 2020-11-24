package com.example.pricetag;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class account extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Button reg = findViewById(R.id.register);
        Button log = findViewById(R.id.signin);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regOp = new Intent(account.this, Register.class);
                startActivity(regOp);
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logOp = new Intent(account.this, Login.class);
                startActivity(logOp);
            }
        });
        Log.d("my","account created");
        fAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        try {
            userID = fAuth.getCurrentUser().getUid();
        }
        catch (Exception e){
            userID = "";
        }
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setSelectedItemId(R.id.My_Account);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Keyword_Search:
                        Intent a = new Intent(getApplicationContext(), Keyword_Search.class);
                        startActivity(a);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Image_Search:
                        Intent e = new Intent(getApplicationContext(), camPage.class);
                        startActivity(e);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Wishlist:
                        Intent b = new Intent(getApplicationContext(), wishList.class);
                        startActivity(b);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.My_Account:
                        if(!userID.equals("")){
                            Intent c  = new Intent(getApplicationContext(), userData.class);
                            startActivity(c);
                            overridePendingTransition(0,0);
                        }
                        else{
                            Intent d  = new Intent(getApplicationContext(), account.class);
                            startActivity(d);
                            overridePendingTransition(0,0);
                        }

                        return true;
                }
                return false;
            }
        });
    }
}
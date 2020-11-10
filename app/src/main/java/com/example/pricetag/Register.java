package com.example.pricetag;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

public class Register extends AppCompatActivity {
    EditText mFullName,musername,mEmail,mPassword;
    Button mRegisterBtn;
    TextView mcancel;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName=findViewById(R.id.fullname);
        musername=findViewById(R.id.username);
        mEmail=findViewById(R.id.email);
        mPassword=findViewById((R.id.password));
        mRegisterBtn=findViewById(R.id.regbtn);
        mcancel=findViewById(R.id.cancel);

        fAuth=FirebaseAuth.getInstance();





    }
}
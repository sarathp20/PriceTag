package com.example.pricetag;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class HomePage extends AppCompatActivity {
    SpaceNavigationView navbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        navbar=findViewById(R.id.space);
        navbar.initWithSaveInstanceState(savedInstanceState);
        navbar.addSpaceItem(new SpaceItem("", R.drawable.history));
        navbar.addSpaceItem(new SpaceItem("", R.drawable.user));

        navbar.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Toast.makeText(HomePage.this,"onCentreButtonClick", Toast.LENGTH_SHORT).show();
                navbar.setCentreButtonSelectable(true);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Toast.makeText(HomePage.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Toast.makeText(HomePage.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
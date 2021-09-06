package com.example.db_successful;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class activityLike extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent intentHome = new Intent(activityLike.this, MainActivity.class);
                        startActivity(intentHome);
                        break;

                    case R.id.navigation_search:
                        Intent intent = new Intent(activityLike.this, com.example.db_successful.activitySearch.class);
                        startActivity(intent);
                        break;

                    case R.id.navigation_plus:
                        Intent intentPlus = new Intent(activityLike.this, com.example.db_successful.activityPost.class);
                        startActivity(intentPlus);

                        break;

                    case R.id.navigation_heart:

                        break;

                    case R.id.navigation_user:
                        Intent intentProfile = new Intent(activityLike.this, com.example.db_successful.activityProfile.class);
                        startActivity(intentProfile);
                        break;
                }
                return false;
            }
        });
    }

}
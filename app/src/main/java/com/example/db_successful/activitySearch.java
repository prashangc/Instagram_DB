package com.example.db_successful;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class activitySearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent intentHome = new Intent(activitySearch.this, MainActivity.class);
                        startActivity(intentHome);
                        break;

                    case R.id.navigation_search:

                        break;

                    case R.id.navigation_plus:
                        Intent intentPlus = new Intent(activitySearch.this, com.example.db_successful.activityPost.class);
                        startActivity(intentPlus);
                        break;

                    case R.id.navigation_heart:
                        Intent intentLike = new Intent(activitySearch.this, activityLike.class);
                        startActivity(intentLike);
                        break;

                    case R.id.navigation_user:
                        Intent intentProfile = new Intent(activitySearch.this, activityProfile.class);
                        startActivity(intentProfile);
                        break;
                }
                return false;
            }
        });
    }

}
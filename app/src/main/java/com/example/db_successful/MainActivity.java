package com.example.db_successful;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static SQLiteHelper sqLiteHelper;
   GridView gridView;
    ArrayList<yourPhotos> list;
    CustomAdapterForMain adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView camera = toolbar.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, activityStory.class);
                startActivity(i);



            }
        });

        ImageView refresh = toolbar.findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    gridView = findViewById(R.id.mainActivity_grid);
                    list = new ArrayList<>();
                    adapter = new CustomAdapterForMain(MainActivity.this, R.layout.main_activity_grid_view, list);
                    gridView.setAdapter(adapter);


                try {
                    SQLiteHelper sqLiteHelper = new SQLiteHelper(MainActivity.this, "dbINSTAGRAM.sqlite", null, 1);
                    Cursor cursor = sqLiteHelper.getData("SELECT * FROM APP");
                    list.clear();
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(0);
                        String name = cursor.getString(1);
                        byte[] image = cursor.getBlob(2);

                        list.add(new yourPhotos(name, image, id));
                    }
                    adapter.notifyDataSetChanged();


                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        ImageView private_message = toolbar.findViewById(R.id.message);
        private_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Private messages unavailable.", Toast.LENGTH_SHORT).show();
            }
        });

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:

                        break;

                    case R.id.navigation_search:
                        Intent intent = new Intent(MainActivity.this, activitySearch.class);
                        startActivity(intent);
                        break;

                    case R.id.navigation_plus:

                        Intent intentPlus = new Intent(MainActivity.this, activityPost.class);
                        startActivity(intentPlus);
                        break;

                    case R.id.navigation_heart:
                        Intent intentLike = new Intent(MainActivity.this, activityLike.class);
                        startActivity(intentLike);
                        break;

                    case R.id.navigation_user:
                        Intent intentProfile = new Intent(MainActivity.this, activityProfile.class);
                        startActivity(intentProfile);
                        break;
                }
                return false;
            }
        });
    }


}
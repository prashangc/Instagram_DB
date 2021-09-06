package com.example.db_successful;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//
//        getSupportActionBar().hide();
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(4000);
                }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                finally {
                    Intent intent = new Intent(SplashActivity.this , com.example.db_successful.Login.class);
                    startActivity(intent);
                }
            }
        };
        thread.start();
    }
}
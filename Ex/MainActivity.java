package com.example.curdmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private final int POST_DELAY_TIME = 2000;
    public static final String BASE_URL = "http://192.168.1.22/MaliduAPI/MobileAPI/tabelManager/"; //http://localhost:63343/MaliduAPI/MobileAPI/tabelManager/Auth/getAuth.php
    public static String DRIVER_ID = "0";
    public static String TRUCK_ID = "0";
    public static String DRIVER_NAME = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadAuthScreen();
    }

    public void loadAuthScreen(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this,Auth_screen.class);
                startActivity(i);
                MainActivity.this.finish();
            }
        },POST_DELAY_TIME);
    }
}
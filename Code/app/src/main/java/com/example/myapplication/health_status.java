package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.LoginStuff.LoginResponse;
import com.example.myapplication.LoginStuff.User;
import com.example.myapplication.amazonS3.amazonS3main;
import com.fitbitsample.FitbitSharedPref.FitbitPref;
import com.fitbitsample.FitbitSharedPref.FitbitSummary;
import com.fitbitsample.FitbitSharedPref.FitbitUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/*
This activity is for displaying health data that is retrieved from fitbit integrating module app.
In fitapp module, under java->com.fitbitsample->fitbitdata->FitbitPref, SharedPreference is created that
saves Fitbit user's profile information and health summary. We just call those object and display in our app.
 */
public class health_status extends AppCompatActivity {
    private TextView steps,avgHeartRate,caloriesBMR,caloriesout,height,weight;
    private BottomNavigationView bottomNavigation;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_status);

        //initialize button navigation
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);
        bottomNavigation.setSelectedItemId(R.id.nav_synFitbit);

        Context context = getApplicationContext();
        amazonS3main az = new amazonS3main();
        try {
            az.main(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //bottom navigation listener
    private  BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.nav_synFitbit:
                    return true;
                case R.id.nav_home:
                    startActivity(new Intent(getApplicationContext(), homescreen.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.nav_you:
                    startActivity(new Intent(getApplicationContext(), SettingsPage.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        }
    };
}

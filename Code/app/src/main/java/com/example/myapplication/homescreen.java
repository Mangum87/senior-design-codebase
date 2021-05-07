package com.example.myapplication;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.mainScreen.MainScreen;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homescreen extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private final Fragment fragmentHome = new MainScreen();
    private final Fragment fragmentYou = new YouScreen();
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment active = fragmentHome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);

        //to avoid recreation of fragments and to reuse the fragments that is already created
        fragmentManager.beginTransaction().add(R.id.fragment_container, fragmentYou,"2").hide(fragmentYou).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container,fragmentHome,"1").commit();

        scheduleJob(); // Schedule auto FitBit sync
    }

    //bottom navigation listener
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.nav_synFitbit:
                    return true;
                case R.id.nav_home:
                    fragmentManager.beginTransaction().hide(active).show(fragmentHome).commit();
                    active = fragmentHome;
                    return true;
                case R.id.nav_you:
                    fragmentManager.beginTransaction().hide(active).show(fragmentYou).commit();
                    active = fragmentYou;
                    return true;
            }
            return false;
        }
    };

    private static final String TAG = "homescreen";
    public void scheduleJob() {
        ComponentName componentName = new ComponentName(this, com.example.myapplication.Job.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(false) //does not require charging
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED) //requires Wi-Fi
                .setPersisted(true) //start on system restart
                .setPeriodic(15 * 60 * 1000) //repeat every 15 minutes (Note: Minimum time required is 15 minutes, cannot be lower than 15 minutes.)
                .build();
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }

    public void cancelJob() {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG, "Job cancelled ");
    }


// commented to go to dashboard without login
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
//            Intent intent = new Intent(this, com.example.myapplication.LoginStuff.Login.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
//    }
}

 package com.example.myapplication;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.LoginStuff.Login;
import com.example.myapplication.amazonS3.pullBucketData;
import com.example.myapplication.chart.HeartRateExtendedLineChart;
import com.example.myapplication.chart.SleepExtendedBarChart;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homescreen extends AppCompatActivity implements View.OnClickListener  {
//    private TextView heartRateMore,sleepMore,weightMore;
    private BottomNavigationView bottomNavigation;
    BarChart barChartSleep;
    LineChart lineChartHeart;
    CardView cardViewHeart,cardViewSleep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_homescreen);

        //initialize chart
        lineChartHeart = findViewById(R.id.line_chart3);
        barChartSleep = findViewById(R.id.bar_chart3);

        //initialize cardView
        cardViewHeart = findViewById(R.id.hearRateCardView);
        cardViewSleep = findViewById(R.id.sleepCardView);

        //button navigation
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);

        //set home selected when user see the main screen after login
        bottomNavigation.setSelectedItemId(R.id.nav_home);


        // initial s3 bucket object pull class
        pullBucketData data = new pullBucketData();

//        try {
//            int []a = data.S3Bucket_object();
//        } catch (Exception e) {
//            Toast.makeText(homescreen.this, "try", Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }

        //heart rate dashboard
        findViewById(R.id.textHeartRateClicked).setOnClickListener(this);
        findViewById(R.id.textHeartRateMoreClicked).setOnClickListener(this);

        //sleep dashboard
        findViewById(R.id.textSleepClicked).setOnClickListener(this);
        findViewById(R.id.textSleepMoreClicked).setOnClickListener(this);

        //weight dashboard
        findViewById(R.id.textWeightClicked).setOnClickListener(this);
        findViewById(R.id.textWeightMoreClicked).setOnClickListener(this);

        /*
        //Takes to program page when Program button is pressed from homescreen
        program.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreen.this, select_program.class));
            }
        });

       */
    }

    //bottom navigation listener
    private  BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.nav_synFitbit:
                    //startActivity(new Intent(getApplicationContext(), health_status.class));
                    overridePendingTransition(0,0);
                    scheduleJob();
                    return true;
                case R.id.nav_home:
                    return true;
                case R.id.nav_you:
                    startActivity(new Intent(getApplicationContext(), SettingsPage.class));
                    overridePendingTransition(0,0);
                    return true;
            }

            return false;
        }
    };

    //extends the card view to show graph
    private void extendHeartRateCardView(){
        if(lineChartHeart.getVisibility()==View.GONE){
            HeartRateExtendedLineChart chart = new HeartRateExtendedLineChart();
            chart.plotHeartRateExtendedLineChart(lineChartHeart);
            TransitionManager.beginDelayedTransition(cardViewSleep,new AutoTransition());
            lineChartHeart.setVisibility(View.VISIBLE);
        }
        else{
            //TransitionManager.beginDelayedTransition(cardViewSleep,new AutoTransition());
            lineChartHeart.setVisibility(View.GONE);
        }
    }

    //takes to screen where user can find more detail information about Heartrate Activity
    private void showHeartRateMoreActivity(){
        startActivity(new Intent(homescreen.this, com.example.myapplication.heartRate.HeartRateMore.class));
    }

    //this method extends the line chart for sleep card view
    private void extendSleepCardView(){
        if(barChartSleep.getVisibility()==View.GONE){
            SleepExtendedBarChart chart = new SleepExtendedBarChart();
            chart.plotSleepExtendedBarChart(barChartSleep);
            TransitionManager.beginDelayedTransition(cardViewHeart,new AutoTransition());
            barChartSleep.setVisibility(View.VISIBLE);
        }
        else{
            //TransitionManager.beginDelayedTransition(cardViewHeart,new AutoTransition());
            barChartSleep.setVisibility(View.GONE);
        }
    }

    private void showSleepMoreActivity(){
        startActivity(new Intent(homescreen.this, com.example.myapplication.sleep.SleepMore.class));
    }

    private void extendWeightCardView(){

    }

    private void showWeightMoreActivity(){

    }

    @Override
    public void onClick (View v){
        switch (v.getId()) {
            case R.id.textHeartRateClicked:
                extendHeartRateCardView();
                break;
            case R.id.textHeartRateMoreClicked:
                showHeartRateMoreActivity();
                break;
            case R.id.textSleepClicked:
                extendSleepCardView();
                break;
            case R.id.textSleepMoreClicked:
                showSleepMoreActivity();
                break;
            case R.id.textWeightClicked:
                extendWeightCardView();
                break;
            case R.id.textWeightMoreClicked:
                showWeightMoreActivity();
                break;
        }
    }
// commented to go to dashboard without login
    @Override
    protected void onStart() {
        super.onStart();
        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }



    private static final String TAG = "homescreen";
    public void scheduleJob() {
        ComponentName componentName = new ComponentName(this, Job.class);
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
}

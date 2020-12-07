package com.example.myapplication.heartRate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.chart.BarChartActivity;
import com.example.myapplication.dialog.HeartRateMoreDialog;
import com.example.myapplication.homescreen;
import com.example.myapplication.sleep.SleepMore;
import com.github.mikephil.charting.charts.BarChart;

public class HeartRateMore extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_more);

        findViewById(R.id.hr_more_arrow_back).setOnClickListener(this);
        findViewById(R.id.hr_more_help).setOnClickListener(this);
        BarChart barChart  = findViewById(R.id.bar_chart2);

        BarChartActivity barChartActivity = new BarChartActivity();
        barChartActivity.plot_barGraph(barChart);
    }

    private void show_homeScreen(){
        Intent intent = new Intent(HeartRateMore.this, homescreen.class);
        startActivity(intent);
    }

    private void show_hrMore_helpScreen(){
        HeartRateMoreDialog heartRateMoreDialog = new HeartRateMoreDialog();
        heartRateMoreDialog.show(getSupportFragmentManager(),"Heart Rate Help");
    }

    @Override
    public void onClick (View v){
        switch (v.getId()) {
            case R.id.hr_more_arrow_back:
                show_homeScreen();
                break;
            case R.id.hr_more_help:
                show_hrMore_helpScreen();
                break;
        }
    }
}
package com.example.myapplication.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.chart.LineChartActivity;
import com.example.myapplication.homescreen;
import com.github.mikephil.charting.charts.LineChart;

public class SleepMore extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_more);

        findViewById(R.id.sleep_more_arrow_back).setOnClickListener(this);
        findViewById(R.id.sleep_more_help).setOnClickListener(this);
        LineChart lineChart = findViewById(R.id.line_chart2);

        LineChartActivity lineChartActivity = new LineChartActivity();
        lineChartActivity.plot_lineChart(lineChart);
    }

    private void show_homeScreen(){
        Intent intent = new Intent(SleepMore.this, homescreen.class);
        startActivity(intent);
    }

    private void show_sleepMore_helpScreen(){
        Toast.makeText(SleepMore.this, "Inside Sleep Help", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick (View v){
        switch (v.getId()) {
            case R.id.sleep_more_arrow_back:
                show_homeScreen();
                break;
            case R.id.sleep_more_help:
                show_sleepMore_helpScreen();
                break;
        }
    }
}
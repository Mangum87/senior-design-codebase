package com.example.myapplication.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.readAndSaveAllFile.MultipleFileData;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;

public class SleepMoreSpecificDateClicked extends AppCompatActivity {
    private TextView date,collapseScreen, totalValue;
    private LineChart lineChart;

    private int totalVal;
    private String dateValue;
    private static ArrayList<Double> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_more_specific_date_clicked);

        date = findViewById(R.id.sleepSpecificDate);
        collapseScreen = findViewById(R.id.collapseSleepSpecificDateView);
        totalValue = findViewById(R.id.sleepSpecificDateTotalValue);
        lineChart = findViewById(R.id.lineChartSleepSpecificDateScreen);

        collapseScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

    }

    private void getData() {

    }

    private void setValues() {
    }

    @Override
    protected void onStart() {
        super.onStart();

        getData();
        setValues();
    }
}
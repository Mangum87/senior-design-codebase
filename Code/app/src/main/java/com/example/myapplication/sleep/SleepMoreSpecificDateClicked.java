package com.example.myapplication.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.chart.PlotChart;
import com.example.myapplication.recyclerView.MyAdapter;
import com.github.mikephil.charting.charts.PieChart;

import java.util.HashMap;
import java.util.Map;

public class SleepMoreSpecificDateClicked extends AppCompatActivity {
    private TextView date, collapseScreen, hour, minute;
    private PieChart pieChart;
    private int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_more_specific_date_clicked);

        date = findViewById(R.id.sleepSpecificDate);
        collapseScreen = findViewById(R.id.collapseSleepSpecificDateView);
        hour = findViewById(R.id.sleepSpecificDateHrsValue);
        minute = findViewById(R.id.sleepSpecificDateMinValue);
        pieChart = findViewById(R.id.pieChartSleepSpecificDateScreen);

        getIndexOfData();
        setValues();

        collapseScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

    }

    /**
     * stores the index of data which is sent from previous intent 'Recycler view Adapter' when user clicks specific date
     */
    private void getIndexOfData() {
        index = getIntent().getIntExtra("index", 1);
    }

    private void setValues() {

        date.setText(MyAdapter.SleepData.get(index).getDate());
        hour.setText(String.valueOf(MyAdapter.SleepData.get(index).getTotalHoursSlept()));
        minute.setText(String.valueOf(MyAdapter.SleepData.get(index).getTotalMinuteSlept()));

        //store sleep pieChart Data in map to plot
        Map<String, Integer> sleepChartData = new HashMap<>();
        sleepChartData.put("WAKE", new Integer(MyAdapter.SleepData.get(index).getTotalWake()));
        sleepChartData.put("LIGHT", new Integer(MyAdapter.SleepData.get(index).getTotalLight()));
        sleepChartData.put("DEEP", new Integer(MyAdapter.SleepData.get(index).getTotalDeep()));
        sleepChartData.put("REM", new Integer(MyAdapter.SleepData.get(index).getTotalRem()));

        PlotChart.pieChart(this, false, "sleep", sleepChartData, pieChart);
    }
}
package com.example.myapplication.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.chart.PlotChart;
import com.example.myapplication.readAndSaveAllFile.MultipleFileData;
import com.example.myapplication.readAndSaveAllFile.Sleep.SleepFileManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SleepMoreSpecificDateClicked extends AppCompatActivity {
    private TextView date,collapseScreen, hour, minute;
    private PieChart pieChart;

    /**set a flag to check if index is received from previous intent which is passed from 'Recycler View Adapter'
     * without flag index is always initialized '0' and always plots data on index '0' on arrayList
     */
    private boolean hasData;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_more_specific_date_clicked);

        date = findViewById(R.id.sleepSpecificDate);
        collapseScreen = findViewById(R.id.collapseSleepSpecificDateView);
        hour = findViewById(R.id.sleepSpecificDateHrsValue);
        minute = findViewById(R.id.sleepSpecificDateMinValue);
        pieChart = findViewById(R.id.pieChartSleepSpecificDateScreen);

        getData();
        setValues();

        collapseScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

    }

    /** stores the index of data which is sent from previous intent 'Recycler view Adapter' when user clicks specific date*/
    private void getData() {
        if(getIntent().hasExtra("index")){
            index = getIntent().getIntExtra("index",1);
            hasData = true;
        }
        else{
            Toast.makeText(this,"No Data Available",Toast.LENGTH_SHORT).show();
            hasData = false;
        }
    }

    private void setValues() {
        if(hasData){
            date.setText(SleepFileManager.files.get(index).getDate());
            hour.setText(String.valueOf(SleepFileManager.files.get(index).getTotalHoursSlept()));
            minute.setText(String.valueOf(SleepFileManager.files.get(index).getTotalMinuteSlept()));

            //store sleep pieChart Data in map to plot
            Map<String,Integer> sleepChartData = new HashMap<>();
            sleepChartData.put("WAKE", new Integer(SleepFileManager.files.get(index).getTotalWake()));
            sleepChartData.put("LIGHT", new Integer(SleepFileManager.files.get(index).getTotalLight()));
            sleepChartData.put("DEEP",new Integer(SleepFileManager.files.get(index).getTotalDeep()));
            sleepChartData.put("REM",new Integer(SleepFileManager.files.get(index).getTotalRem()));

            PlotChart.pieChart(this,false,sleepChartData,pieChart);
        }
    }
}
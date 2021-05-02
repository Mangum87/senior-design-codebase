package com.example.myapplication.heartRate;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.chart.PlotChart;
import com.example.myapplication.readAndSaveAllFile.ReadAndSaveMultipleFile;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.Collections;

public class HeartRateWeeklySpecificDateClicked extends AppCompatActivity {
    TextView date,high,low,collapseScreen;
    LineChart lineChart;

    private ArrayList<String> dayDates = new ArrayList<>();
    private ArrayList<String> weekDates = new ArrayList<>();
    private ArrayList<String> dateLabels = new ArrayList<>();
    private ArrayList<Double> weekHeartRates = new ArrayList<>();

    /**set a flag to check if index is received from previous intent which is passed from 'Recycler View Adapter'
     * without flag index is always initialized '0' and always plots data on index '0' on arrayList
     */
    private boolean hasData;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_specific_date_clicked);

        date = findViewById(R.id.heartRateSpecificDate);
        collapseScreen = findViewById(R.id.collapseHeartRateSpecificDateView);
        high = findViewById(R.id.heartRateSpecificDateHighValue);
        low = findViewById(R.id.heartRateSpecificDateLowValue);
        lineChart = findViewById(R.id.lineChartHeartRateSpecificDateScreen);

        getData();
        getWeeklyData();
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
    private void getData(){
        if(getIntent().hasExtra("index") ){
            index = getIntent().getIntExtra("index",1);
            hasData = true;
        }
        else{
            Toast.makeText(this,"No Data Available",Toast.LENGTH_SHORT).show();
            hasData = false;
        }
    }

    private void getWeeklyData() {

        /** subtracts the remainder if the number of files is not a multiple of 7 */
        int daysForAverage = ReadAndSaveMultipleFile.allData.size() - ReadAndSaveMultipleFile.allData.size()%7;

        for(int i = 0; i < daysForAverage; i++) {
            dayDates.add(ReadAndSaveMultipleFile.allData.get(i).getDate().substring(5)); /** only taking month and day excluding year */
        }

        /** gets the dates for the week, then adds it to the array list before moving onto the next week */
        for(int i = 0; i < dayDates.size(); i=i+7) {
            weekDates.add(dayDates.get(i+6)+" to "+dayDates.get(i));
        }

        /** gets the days in the week as well as the average heart rates of that day that the index is currently on */
        for(int i = index*7; i < index*7+6; i++) {
            dateLabels.add(ReadAndSaveMultipleFile.allData.get(i).getDate().substring(5));
            weekHeartRates.add(ReadAndSaveMultipleFile.allData.get(i).getAverageHeartRate());
        }
    }

    private void setValues(){
        /** check the flag for index received or not */
        if(hasData){
            date.setText(weekDates.get(index));
            high.setText(String.valueOf(Collections.max(weekHeartRates).intValue()));
            low.setText(String.valueOf(Collections.min(weekHeartRates).intValue()));

            PlotChart.lineChart(this,false,lineChart,weekHeartRates,dateLabels);
            lineChart.getAxisLeft().setAxisMinValue(0);
        }
    }
}

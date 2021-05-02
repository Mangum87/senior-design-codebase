package com.example.myapplication.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.chart.PlotChart;
import com.example.myapplication.readAndSaveAllFile.MultipleFileData;
import com.example.myapplication.readAndSaveAllFile.ReadAndSaveMultipleFile;
import com.example.myapplication.readAndSaveAllFile.Sleep.SleepFileManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SleepWeeklySpecificDateClicked extends AppCompatActivity {
    private TextView date,collapseScreen, hour, minute;
    private PieChart pieChart;

    private int totalHours, totalMin;

    private ArrayList<String> dayDates = new ArrayList<>();
    private ArrayList<String> weekDates = new ArrayList<>();
    private ArrayList<String> dateLabels = new ArrayList<>();

    private ArrayList<Integer> weekHours = new ArrayList<>();
    private ArrayList<Integer> weekMin = new ArrayList<>();

    private ArrayList<Integer> wakeSleep = new ArrayList<>();
    private ArrayList<Integer> lightSleep = new ArrayList<>();
    private ArrayList<Integer> deepSleep = new ArrayList<>();
    private ArrayList<Integer> remSleep = new ArrayList<>();

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
        getWeeklyData();

        totalHours = getSum(weekHours);
        totalMin = getSum(weekMin);

        totalHours = totalHours + totalMin/60; // Converts minutes to hours and adds it to total hours
        totalMin = totalMin % 60; // The remainder minutes after converting minutes to hours for the previous step.

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

        /** gets the days in the week as well as the amount of each type of sleep for that day that the index is currently on */
        for(int i = index*7; i < index*7+6; i++) {
            dateLabels.add(ReadAndSaveMultipleFile.allData.get(i).getDate().substring(5));
            weekHours.add(SleepFileManager.files.get(i).getTotalHoursSlept());
            weekMin.add(SleepFileManager.files.get(i).getTotalMinuteSlept());
            wakeSleep.add(SleepFileManager.files.get(i).getTotalWake());
            lightSleep.add(SleepFileManager.files.get(i).getTotalLight());
            deepSleep.add(SleepFileManager.files.get(i).getTotalDeep());
            remSleep.add(SleepFileManager.files.get(i).getTotalRem());
        }
    }

    /** calculate the total of the array */
    private int getSum(ArrayList<Integer> n) {
        int total = 0;

        for(int i = 0; i < n.size(); i++) {
            total = total + n.get(i);
        }

        return total;
    }

    private void setValues() {
        if(hasData){
            date.setText(weekDates.get(index));
            hour.setText(String.valueOf(totalHours));
            minute.setText(String.valueOf(totalMin));

            //store sleep pieChart Data in map to plot
            Map<String,Integer> sleepChartData = new HashMap<>();
            sleepChartData.put("WAKE", new Integer(getSum(wakeSleep)));
            sleepChartData.put("LIGHT", new Integer(getSum(lightSleep)));
            sleepChartData.put("DEEP", new Integer(getSum(deepSleep)));
            sleepChartData.put("REM", new Integer(getSum(remSleep)));

            PlotChart.pieChart(this,false,"sleep",sleepChartData,pieChart);
        }
    }
}
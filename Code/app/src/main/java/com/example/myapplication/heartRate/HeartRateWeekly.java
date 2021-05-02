package com.example.myapplication.heartRate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.chart.PlotChart;
import com.example.myapplication.dialog.HeartRateMoreDialog;
import com.example.myapplication.readAndSaveAllFile.ReadAndSaveMultipleFile;
import com.example.myapplication.recyclerView.MyAdapter;
import com.example.myapplication.recyclerView.SpeedyLinearLayoutManager;
import com.example.myapplication.recyclerView.WeeklyAdapter;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.Collections;

public class HeartRateWeekly extends Fragment implements View.OnClickListener {
    private View view;
    private LineChart lineChart;

    private RecyclerView recyclerView;

    private ArrayList<Double> dayAverage = new ArrayList<>();
    private ArrayList<String> dateLabel = new ArrayList<>();

    private ArrayList<Double> weeklyAverage = new ArrayList<>();
    private ArrayList<String> xLabel = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_heart_rate_weekly,container,false);

        view.findViewById(R.id.hr_more_arrow_back).setOnClickListener(this);
        view.findViewById(R.id.hr_more_help).setOnClickListener(this);
        lineChart = view.findViewById(R.id.lineChartHeartRateMoreScreen);

        recyclerView = view.findViewById(R.id.heartRateMoreRecyclerView);

        /** only showing the average graph if there is at least 7 days of data */
        if(ReadAndSaveMultipleFile.allData.size() >= 7){
            getWeeklyAverageData();
            Collections.reverse(weeklyAverage);
            Collections.reverse(xLabel);
            PlotChart.lineChart(view.getContext(),false,lineChart,weeklyAverage,xLabel);
        }

        startRecyclerView();

        return view;
    }

    private void showHrMoreHelpDialog(){
        HeartRateMoreDialog heartRateMoreDialog = new HeartRateMoreDialog();
        heartRateMoreDialog.show(getFragmentManager(),"Heart Rate Help");
    }

    private void startRecyclerView(){
        WeeklyAdapter myAdapter = new WeeklyAdapter(view.getContext(),"heartRate");
        /**check if line that separates each day data has been added before or not */
        if(0 == recyclerView.getItemDecorationCount()){
            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),LinearLayoutManager.VERTICAL));
        }
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new SpeedyLinearLayoutManager(view.getContext(), SpeedyLinearLayoutManager.VERTICAL, false));
    }

    private void getWeeklyAverageData() {

        /** subtracts the remainder if the number of files is not a multiple of 7 */
        int daysForAverage = ReadAndSaveMultipleFile.allData.size() - ReadAndSaveMultipleFile.allData.size()%7;
        double sevenDaysAverage = 0;

        /** checks if there recent 28 days data or not and if not then stores average value of heartRate of the weeks */
        if(ReadAndSaveMultipleFile.allData.size() < 28){
            for(int i = 0; i < daysForAverage; i++) {
                dayAverage.add(ReadAndSaveMultipleFile.allData.get(i).getAverageHeartRate());
                dateLabel.add(ReadAndSaveMultipleFile.allData.get(i).getDate().substring(5)); /** only taking month and day excluding year */
            }
        }

        /** gets implemented when there is more than 28 days of data and stores average value of heartRate of recent 4 weeks */
        else{
            for(int i = 0; i < 28; i++) {
                dayAverage.add(ReadAndSaveMultipleFile.allData.get(i).getAverageHeartRate());
                dateLabel.add(ReadAndSaveMultipleFile.allData.get(i).getDate().substring(5));
            }
        }

        /** gets the average heart rate for the week, then adds it to the array list before moving onto the next week */
        for(int i = 0; i < dayAverage.size(); i=i+7) {
            for(int j = i; j < i+7; j++) {
                sevenDaysAverage = sevenDaysAverage + dayAverage.get(j);
            }

            sevenDaysAverage = sevenDaysAverage/7;
            weeklyAverage.add(sevenDaysAverage);
            xLabel.add(dateLabel.get(i+6)+" to "+dateLabel.get(i));

            /** resetting the variable for the average heart rate calculation for the next week */
            sevenDaysAverage = 0;
        }

    }

    private void showMainScreen(){
        /**
         * when switching between fragments old fragment is added in back stack,
         * check the stack count to remove it from stack which helps to return to old fragment
         */
        if(getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStack();
            return;
        }
    }

    @Override
    public void onClick (View view){
        switch (view.getId()) {
            case R.id.hr_more_arrow_back:
                showMainScreen();
                break;
            case R.id.hr_more_help:
                showHrMoreHelpDialog();
                break;
        }
    }
}
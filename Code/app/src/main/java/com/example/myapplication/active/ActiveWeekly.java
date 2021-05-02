package com.example.myapplication.active;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.chart.PlotChart;
import com.example.myapplication.readAndSaveAllFile.ReadAndSaveMultipleFile;
import com.example.myapplication.readAndSaveAllFile.Sleep.SleepFileManager;
import com.example.myapplication.recyclerView.MyAdapter;
import com.example.myapplication.recyclerView.SpeedyLinearLayoutManager;
import com.example.myapplication.recyclerView.WeeklyAdapter;
import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;
import java.util.Collections;

public class ActiveWeekly extends Fragment implements View.OnClickListener {
    private View view;
    private BarChart barChart;
    private RecyclerView recyclerView;

    private ArrayList<Double> hrAverage = new ArrayList<>();
    private ArrayList<Double> minAverage = new ArrayList<>();
    private ArrayList<String> dateLabel = new ArrayList<>();

    private ArrayList<Double> weeklyHrAverage = new ArrayList<>();
    private ArrayList<Double> weeklyMinAverage = new ArrayList<>();
    private ArrayList<String> xLabel = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_active_more,container,false);

        /**set clickListener for back arrow and help */
        view.findViewById(R.id.active_more_arrow_back).setOnClickListener(this);
        view.findViewById(R.id.active_more_help).setOnClickListener(this);

        /**initialize the chart and recycler view */
        barChart = view.findViewById(R.id.barChartActiveMoreScreen);
        recyclerView = view.findViewById(R.id.recyclerViewActiveMoreScreen);

        /** only showing the bar graph if there is at least 7 days of data */
        if(ReadAndSaveMultipleFile.allData.size() >= 7){
            getWeeklyAverageData();
            Collections.reverse(weeklyHrAverage);
            Collections.reverse(xLabel);
            PlotChart.barChart(view.getContext(), "active", barChart, weeklyHrAverage, xLabel);
        }

        startRecyclerView();

        return view;
    }

    private void showMainScreen(){
        //when switching between fragments old fragment is added in back stack, check the stack count
        // to remove it from stack which helps to return to old fragment
        if(getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStack();
            return;
        }
    }

    /**
     * This method gets recent seven days sleep data & date stored in 'files'
     * stores in ArrayList to plot the graph
     */
    private void getWeeklyAverageData() {

        /** subtracts the remainder if the number of files is not a multiple of 7 */
        int daysForAverage =ReadAndSaveMultipleFile.allData.size() - ReadAndSaveMultipleFile.allData.size()%7;
        double hoursAverage = 0;
        double minsAverage = 0;

        /** checks if there recent 28 days data or not and if not then stores average value of total hours and minutes slept of the weeks */
        if(ReadAndSaveMultipleFile.allData.size() < 28){
            for(int i = 0; i < daysForAverage; i++) {
                hrAverage.add((double)ReadAndSaveMultipleFile.allData.get(i).getHrActive());
                minAverage.add((double)ReadAndSaveMultipleFile.allData.get(i).getMinActive());
                dateLabel.add(ReadAndSaveMultipleFile.allData.get(i).getDate().substring(5)); /** only taking month and day excluding year */
            }
        }

        /** gets implemented when there is more than 28 days of data and stores average value of total hours and minutes slept of recent 4 weeks */
        else{
            for(int i = 0; i < 28; i++) {
                hrAverage.add((double)ReadAndSaveMultipleFile.allData.get(i).getHrActive());
                minAverage.add((double)ReadAndSaveMultipleFile.allData.get(i).getMinActive());
                dateLabel.add(ReadAndSaveMultipleFile.allData.get(i).getDate().substring(5));
            }
        }

        /** gets the average hours slept for the week, then adds it to the array list before moving onto the next week */
        for(int i = 0; i < hrAverage.size(); i=i+7) {
            for(int j = i; j < i+7; j++) {
                hoursAverage = hoursAverage + hrAverage.get(j);
                minsAverage = minsAverage + minAverage.get(j);
            }

            hoursAverage = hoursAverage/7;
            minsAverage = minsAverage/7;
            weeklyHrAverage.add(hoursAverage);
            weeklyMinAverage.add(minsAverage);
            xLabel.add(dateLabel.get(i+6)+" to "+dateLabel.get(i));

            /** resetting the variable for the average heart rate calculation for the next week */
            hoursAverage = 0;
            minsAverage = 0;
        }
    }

    private void startRecyclerView() {
        WeeklyAdapter myAdapter = new WeeklyAdapter(view.getContext(),"active");
        /**check if line that separates each day data has been added before or not */
        if(0 == recyclerView.getItemDecorationCount())
        {
            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        }
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new SpeedyLinearLayoutManager(view.getContext(), SpeedyLinearLayoutManager.VERTICAL, false));
    }

    private void showActiveMoreHelpDialog() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.active_more_arrow_back:
                showMainScreen();
                break;
            case R.id.active_more_help:
                showActiveMoreHelpDialog();
                break;
        }
    }
}
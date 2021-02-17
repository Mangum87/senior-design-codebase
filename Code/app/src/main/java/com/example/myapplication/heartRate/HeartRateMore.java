package com.example.myapplication.heartRate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.chart.PlotChart;
import com.example.myapplication.dialog.HeartRateMoreDialog;
import com.example.myapplication.readAndSaveAllFile.CalculateData;
import com.example.myapplication.readAndSaveAllFile.ReadAndSaveMultipleFile;
import com.example.myapplication.recyclerView.MyAdapter;
import com.example.myapplication.recyclerView.SpeedyLinearLayoutManager;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;

public class HeartRateMore extends Fragment implements View.OnClickListener {
    private String callFrom = "heartRateMore";
    private View view;
    private LineChart lineChart;
    private RecyclerView recyclerView;
    private ArrayList<Double> thirtyDayAverage = new ArrayList<>();
    private ArrayList<String> xLabel = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_heart_rate_more,container,false);

        view.findViewById(R.id.hr_more_arrow_back).setOnClickListener(this);
        view.findViewById(R.id.hr_more_help).setOnClickListener(this);
        lineChart = view.findViewById(R.id.lineChartHeartRateMoreScreen);

        recyclerView = view.findViewById(R.id.heartRateMoreRecyclerView);

        /** only showing the average graph if there is at least 7 days of data */
        if(ReadAndSaveMultipleFile.allData.size() >= 7){
            getThirtyDayAverageData();
            PlotChart.lineChart(view.getContext(),callFrom,lineChart,thirtyDayAverage,xLabel);
        }

        /** calls recycler view and shows all data */
        startRecyclerView();

        return view;
    }

    private void showHrMoreHelpDialog(){
        HeartRateMoreDialog heartRateMoreDialog = new HeartRateMoreDialog();
        heartRateMoreDialog.show(getFragmentManager(),"Heart Rate Help");
    }

    private void startRecyclerView(){
        MyAdapter myAdapter = new MyAdapter(view.getContext(),"heartRate");
        /**check if line that separates each day data has been added before or not */
        if(0 == recyclerView.getItemDecorationCount()){
            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),LinearLayoutManager.VERTICAL));
        }
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new SpeedyLinearLayoutManager(view.getContext(), SpeedyLinearLayoutManager.VERTICAL, false));
    }

    private void getThirtyDayAverageData() {
        int daysForAverage = 30;

        /** checks if there recent 30 days data or not and if not then stores average value of heartRate of one day */
        if(ReadAndSaveMultipleFile.allData.size() < 30){
            for(int i = 0; i < ReadAndSaveMultipleFile.allData.size(); i++) {
                thirtyDayAverage.add(CalculateData.getAverage(ReadAndSaveMultipleFile.allData.get(i).getHeartRate()));
                xLabel.add(ReadAndSaveMultipleFile.allData.get(i).getDate().substring(5)); /** only taking month and day excluding year */
            }
        }

        /** gets implemented when there is more than 30 days of data and stores average value of heartRate of recent 30 days */
        else{
            for(int i = 0; i < daysForAverage; i++) {
                thirtyDayAverage.add(CalculateData.getAverage(ReadAndSaveMultipleFile.allData.get(i).getHeartRate()));
                xLabel.add(ReadAndSaveMultipleFile.allData.get(i).getDate().substring(5));
            }
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
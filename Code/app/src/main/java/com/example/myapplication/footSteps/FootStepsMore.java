package com.example.myapplication.footSteps;

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
import com.example.myapplication.dialog.FootStepsMoreDialog;
import com.example.myapplication.readAndSaveAllFile.CalculateData;
import com.example.myapplication.readAndSaveAllFile.ReadAndSaveMultipleFile;
import com.example.myapplication.recyclerView.MyAdapter;
import com.example.myapplication.recyclerView.SpeedyLinearLayoutManager;
import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;

public class FootStepsMore extends Fragment implements View.OnClickListener {
    private View view;
    private String callFrom = "footSteps";
    private BarChart barChart;
    private RecyclerView recyclerView;
    private ArrayList<Double> sevenDaysData = new ArrayList<>();
    private ArrayList<String> xLabel = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_foot_steps_more,container,false) ;

        /**set clickListener for back arrow and help */
        view.findViewById(R.id.footStepsMoreArrowBack).setOnClickListener(this);
        view.findViewById(R.id.footStepsMoreHelp).setOnClickListener(this);

        /**initialize the chart and recycler view */
        barChart = view.findViewById(R.id.barChartFootStepsMoreScreen);
        recyclerView = view.findViewById(R.id.footStepsMoreRecyclerView);

        /** only showing the average graph if there is at least 7 days of data */
        if(ReadAndSaveMultipleFile.allData.size() >= 7 ){
            getSevenDaysData();
            PlotChart.barChart(view.getContext(),callFrom,barChart, sevenDaysData, xLabel);
        }

        startRecyclerView();

        return view;
    }

    private void showFootStepsMoreHelpDialog() {
        FootStepsMoreDialog footStepsMoreDialog = new FootStepsMoreDialog();
        footStepsMoreDialog.show(getFragmentManager(),"Foot Steps Help");
    }

    private void startRecyclerView() {
        MyAdapter myAdapter = new MyAdapter(view.getContext(),"footSteps");
        /**check if line that separates each day data has been added before or not */
        if(0 == recyclerView.getItemDecorationCount())
        {
            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        }
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new SpeedyLinearLayoutManager(view.getContext(), SpeedyLinearLayoutManager.VERTICAL, false));
    }

    /**
     * This method gets recent seven days footSteps data & date stored in 'allData'
     * stores in ArrayList to plot the graph
     */
    private void getSevenDaysData() {
        int days = 7;

        if(ReadAndSaveMultipleFile.allData.size() < 7){
            for(int i = 0; i < ReadAndSaveMultipleFile.allData.size(); i++){
                sevenDaysData.add(CalculateData.getTotal(ReadAndSaveMultipleFile.allData.get(i).getSteps()));
                xLabel.add(ReadAndSaveMultipleFile.allData.get(i).getDate().substring(5)); // only taking month and day excluding year
            }
        }
        else{
            for(int i = 0; i < days; i++){
                sevenDaysData.add(CalculateData.getTotal(ReadAndSaveMultipleFile.allData.get(i).getSteps()));
                xLabel.add(ReadAndSaveMultipleFile.allData.get(i).getDate().substring(5));
            }
        }
    }

    private void showMainScreen() {
        /**
         * when switching between fragments old fragment is added in back stack,
         * check the stack count to remove it from stack which helps to return to old fragment
         */
        if ( getFragmentManager().getBackStackEntryCount() > 0)
        {
            getFragmentManager().popBackStack();
            return;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.footStepsMoreArrowBack:
                showMainScreen();
                break;
            case R.id.footStepsMoreHelp:
                showFootStepsMoreHelpDialog();
                break;
        }
    }
}
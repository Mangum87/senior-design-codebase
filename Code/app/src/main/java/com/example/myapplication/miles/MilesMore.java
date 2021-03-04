package com.example.myapplication.miles;

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
import com.example.myapplication.dialog.MilesMoreHelpDialog;
import com.example.myapplication.readAndSaveAllFile.CalculateData;
import com.example.myapplication.readAndSaveAllFile.ReadAndSaveMultipleFile;
import com.example.myapplication.recyclerView.MyAdapter;
import com.example.myapplication.recyclerView.SpeedyLinearLayoutManager;
import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;

public class MilesMore extends Fragment implements View.OnClickListener {
    private String callFrom = "miles";
    private View view;
    private BarChart barChart;
    private RecyclerView recyclerView;
    private ArrayList<Double> sevenDaysData = new ArrayList<>();
    private ArrayList<String> xLabel = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_miles_more, container, false);

        /**set clickListener for back arrow and help */
        view.findViewById(R.id.milesMoreArrowBack).setOnClickListener(this);
        view.findViewById(R.id.milesMoreHelp).setOnClickListener(this);

        /**initialize the chart and recycler view */
        barChart = view.findViewById(R.id.barChartMilesMoreScreen);
        recyclerView = view.findViewById(R.id.milesMoreRecyclerView);

        /** only showing the average graph if there is at least 7 days of data */
        if (ReadAndSaveMultipleFile.allData.size() >= 7) {
            getSevenDaysData();
            PlotChart.barChart(view.getContext(), callFrom, barChart, sevenDaysData, xLabel);
        }

        startRecyclerView();

        return view;
    }

    private void showMilesMoreHelpDialog() {
        MilesMoreHelpDialog milesMoreHelpDialog = new MilesMoreHelpDialog();
        milesMoreHelpDialog.show(getFragmentManager(), "Miles Help");
    }

    private void startRecyclerView() {
        MyAdapter myAdapter = new MyAdapter(view.getContext(), callFrom);
        /**check if line that separates each day data has been added before or not */
        if (0 == recyclerView.getItemDecorationCount()) {
            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        }
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new SpeedyLinearLayoutManager(view.getContext(), SpeedyLinearLayoutManager.VERTICAL, false));
    }

    /**
     * This method gets recent seven days Miles walked data & date stored in 'allData'
     * stores in ArrayList to plot the graph
     */
    private void getSevenDaysData() {
        int days = 7;

        for (int i = 0; i < days; i++) {
            sevenDaysData.add(ReadAndSaveMultipleFile.allData.get(i).getTotalDistance());
            xLabel.add(ReadAndSaveMultipleFile.allData.get(i).getDate().substring(5));
        }
    }

    private void showMainScreen() {
        /**
         * when switching between fragments old fragment is added in back stack, check the stack count
         * to remove it from stack which helps to return to old fragment
         */
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            return;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.milesMoreArrowBack:
                showMainScreen();
                break;
            case R.id.milesMoreHelp:
                showMilesMoreHelpDialog();
                break;
        }
    }
}
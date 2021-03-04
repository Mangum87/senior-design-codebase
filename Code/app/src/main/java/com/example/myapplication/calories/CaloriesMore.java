package com.example.myapplication.calories;

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
import com.example.myapplication.dialog.CaloriesMoreHelpDialog;
import com.example.myapplication.readAndSaveAllFile.CalculateData;
import com.example.myapplication.readAndSaveAllFile.ReadAndSaveMultipleFile;
import com.example.myapplication.recyclerView.MyAdapter;
import com.example.myapplication.recyclerView.SpeedyLinearLayoutManager;
import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;

public class CaloriesMore extends Fragment implements View.OnClickListener {
    private View view;
    private String callFrom = "calories";
    private BarChart barChart;
    private RecyclerView recyclerView;
    private ArrayList<Double> sevenDaysData = new ArrayList<>();
    private ArrayList<String> xLabel = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calories_more, container, false);

        /**set clickListener for back arrow and help */
        view.findViewById(R.id.caloriesMoreArrowBack).setOnClickListener(this);
        view.findViewById(R.id.caloriesMoreHelp).setOnClickListener(this);

        /**initialize the chart and recycler view */
        barChart = view.findViewById(R.id.barChartCaloriesMoreScreen);
        recyclerView = view.findViewById(R.id.caloriesMoreRecyclerView);

        /** only showing the average graph if there is at least 7 days of data */
        if (ReadAndSaveMultipleFile.allData.size() >= 7) {
            getSevenDaysData();
            PlotChart.barChart(view.getContext(), callFrom, barChart, sevenDaysData, xLabel);
        }

        startRecyclerView();

        return view;
    }

    private void showHelpDialog() {
        CaloriesMoreHelpDialog caloriesMoreHelpDialog = new CaloriesMoreHelpDialog();
        caloriesMoreHelpDialog.show(getFragmentManager(), "Calories Help");
    }

    private void startRecyclerView() {
        MyAdapter myAdapter = new MyAdapter(view.getContext(), "calories");
        /**check if line that separates each day data has been added before or not */
        if (0 == recyclerView.getItemDecorationCount()) {
            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        }
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new SpeedyLinearLayoutManager(view.getContext(), SpeedyLinearLayoutManager.VERTICAL, false));
    }

    private void getSevenDaysData() {
        int days = 7;

        for (int i = 0; i < days; i++) {
            sevenDaysData.add(ReadAndSaveMultipleFile.allData.get(i).getTotalCalories());
            xLabel.add(ReadAndSaveMultipleFile.allData.get(i).getDate().substring(5));
        }

    }

    private void showMainScreen() {
        /**
         * when switching between fragments old fragment is added in back stack,
         * check the stack count to remove it from stack which helps to return to old fragment
         */
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            return;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.caloriesMoreArrowBack:
                showMainScreen();
                break;
            case R.id.caloriesMoreHelp:
                showHelpDialog();
                break;
        }
    }
}
package com.example.myapplication.sleep;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dialog.MilesMoreHelpDialog;
import com.example.myapplication.dialog.SleepMoreHelpDialog;
import com.example.myapplication.homescreen;
import com.example.myapplication.readAndSaveAllFile.MultipleFileData;
import com.example.myapplication.readAndSaveAllFile.Sleep.SleepFile;
import com.example.myapplication.readAndSaveAllFile.Sleep.SleepFileManager;
import com.example.myapplication.recyclerView.MyAdapter;
import com.example.myapplication.recyclerView.SpeedyLinearLayoutManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;

public class SleepMore extends Fragment implements View.OnClickListener{
    private View view;
    private BarChart barChart;
    private RecyclerView recyclerView;
    private ArrayList<Double> sevenDaysData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sleep_more,container,false);

        /**set clickListener for back arrow and help */
        view.findViewById(R.id.sleep_more_arrow_back).setOnClickListener(this);
        view.findViewById(R.id.sleep_more_help).setOnClickListener(this);

        /**initialize the chart and recycler view */
        barChart = view.findViewById(R.id.barChartSleepMoreScreen);
        recyclerView = view.findViewById(R.id.recyclerViewSleepMoreScreen);

        getAllSleepData();
        startRecyclerView();

        return view;
    }

    /** reads all the sleep files */
    private void getAllSleepData() {
        SleepFileManager sleepFileManager = new SleepFileManager(view.getContext(),false);
    }

    private void startRecyclerView() {
        MyAdapter myAdapter = new MyAdapter(view.getContext(),"sleep");
        /**check if line that separates each day data has been added before or not */
        if(0 == recyclerView.getItemDecorationCount())
        {
            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        }
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new SpeedyLinearLayoutManager(view.getContext(), SpeedyLinearLayoutManager.VERTICAL, false));
    }

    private void show_mainScreen(){
        //when switching between fragments old fragment is added in back stack, check the stack count
        // to remove it from stack which helps to return to old fragment
        if(getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStack();
            return;
        }
    }

    private void show_sleepMore_helpScreen(){
        SleepMoreHelpDialog sleepMoreHelpDialog = new SleepMoreHelpDialog();
        sleepMoreHelpDialog.show(getFragmentManager(),"Sleep Help");
    }

    @Override
    public void onClick (View v){
        switch (v.getId()) {
            case R.id.sleep_more_arrow_back:
                show_mainScreen();
                break;
            case R.id.sleep_more_help:
                show_sleepMore_helpScreen();
                break;
        }
    }
}
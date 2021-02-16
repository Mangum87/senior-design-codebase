package com.example.myapplication.sleep;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;

public class SleepMore extends Fragment implements View.OnClickListener{
    private View view;
    private BarChart barChart;
    private RecyclerView recyclerView;
    private ArrayList<Double> sevenDaysData = new ArrayList<>();
    //MultipleFileData multipleFileData = new MultipleFileData();
    SleepFileManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sleep_more,container,false);

        view.findViewById(R.id.sleep_more_arrow_back).setOnClickListener(this);
        view.findViewById(R.id.sleep_more_help).setOnClickListener(this);
        this.manager = new SleepFileManager(getContext());
        //LineChart lineChart = view.findViewById(R.id.line_chart2);

        return view;
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
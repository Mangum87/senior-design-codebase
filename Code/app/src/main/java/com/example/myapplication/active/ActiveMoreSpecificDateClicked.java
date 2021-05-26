package com.example.myapplication.active;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.chart.PlotChart;
import com.example.myapplication.recyclerView.MyAdapter;
import com.github.mikephil.charting.charts.PieChart;

import java.util.HashMap;
import java.util.Map;

public class ActiveMoreSpecificDateClicked extends AppCompatActivity {
    private TextView date,collapseScreen, hour, minute;
    private PieChart pieChart;

    /**set a flag to check if index is received from previous intent which is passed from 'Recycler View Adapter'
     * without flag index is always initialized '0' and always plots data on index '0' on arrayList
     */
    private boolean hasData;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_more_specific_date_clicked);

        date = findViewById(R.id.activeSpecificDate);
        collapseScreen = findViewById(R.id.collapseActiveSpecificDateView);
        hour = findViewById(R.id.activeSpecificDateHrsValue);
        minute = findViewById(R.id.activeSpecificDateMinValue);
        pieChart = findViewById(R.id.pieChartActiveSpecificDateScreen);

        getData();
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

    private void setValues() {
        if(hasData){
            date.setText(MyAdapter.HourlyAllActiveData.get(index).getDate());
            hour.setText(String.valueOf(MyAdapter.HourlyAllActiveData.get(index).getHrActive()));
            minute.setText(String.valueOf(MyAdapter.HourlyAllActiveData.get(index).getMinActive()));

            //store active pieChart Data in map to plot
            Map<String,Integer> activeChartData = new HashMap<>(); //store active pieChart Data
            activeChartData.put("Sedentary", (int) MyAdapter.HourlyAllActiveData.get(index).getTotalMinutesSedentary());
            activeChartData.put("Lightly Active", (int) MyAdapter.HourlyAllActiveData.get(index).getTotalMinutesLightlyActive());
            activeChartData.put("Fairly Active", (int) MyAdapter.HourlyAllActiveData.get(index).getTotalMinutesFairlyActive());
            activeChartData.put("Very Active", (int) MyAdapter.HourlyAllActiveData.get(index).getTotalMinutesVeryActive());

            PlotChart.pieChart(this,false, "active", activeChartData, pieChart);
        }
    }
}
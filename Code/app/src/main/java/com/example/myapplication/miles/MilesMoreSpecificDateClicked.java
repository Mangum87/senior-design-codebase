package com.example.myapplication.miles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.chart.PlotChart;
import com.example.myapplication.recyclerView.MyAdapter;
import com.github.mikephil.charting.charts.BarChart;

public class MilesMoreSpecificDateClicked extends AppCompatActivity {
    private String callFrom = "miles";
    private TextView date,collapseScreen, totalValue;
    private BarChart barChart;

    /**set a flag to check if index is received from previous intent which is passed from 'Recycler View Adapter'
     * without flag index is always initialized '0' and always plots data on index '0' on arrayList
     */
    private boolean hasData;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miles_more_specific_date_clicked);

        date = findViewById(R.id.milesSpecificDate);
        totalValue = findViewById(R.id.milesSpecificDateTotalValue);
        collapseScreen = findViewById(R.id.collapseMilesSpecificDateView);
        barChart = findViewById(R.id.barChartMilesSpecificDateScreen);

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

    /** stores the index received from previous intent 'Recycler view Adapter' */
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
        /** check the flag for index received or not */
        if(hasData){
            date.setText(MyAdapter.HourlyData.get(index).getDate());
            totalValue.setText(String.valueOf(MyAdapter.HourlyData.get(index).getTotal()));

            PlotChart.barChart(this, callFrom, barChart, MyAdapter.HourlyData.get(index).getData(), MyAdapter.HourlyData.get(index).getTimeStamp());
        }
    }
}
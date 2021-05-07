package com.example.myapplication.calories;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.chart.PlotChart;
import com.example.myapplication.recyclerView.MyAdapter;
import com.github.mikephil.charting.charts.BarChart;

public class CaloriesMoreSpecificDateClicked extends AppCompatActivity {
    private String callFrom = "calories";
    private TextView date,collapseScreen, totalValue, averageValue;
    private BarChart barChart;

    /**set a flag to check if index is received from previous intent which is passed from 'Recycler View Adapter'
     * without flag index,it always initialize '0' and always plots data on index '0' on arrayList
     */
    private boolean hasData;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_more_specific_date_clicked);

        date = findViewById(R.id.caloriesSpecificDate);
        totalValue = findViewById(R.id.caloriesSpecificDateTotalValue);
        averageValue = findViewById(R.id.caloriesSpecificDateAverageValue);
        collapseScreen = findViewById(R.id.collapseCaloriesSpecificDateView);
        barChart = findViewById(R.id.barChartCaloriesSpecificDateScreen);

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

        date.setText(MyAdapter.HourlyData.get(index).getDate());
        totalValue.setText(String.valueOf((int) MyAdapter.HourlyData.get(index).getTotal()));
        averageValue.setText(String.valueOf((int) MyAdapter.HourlyData.get(index).getAverage()));

        PlotChart.barChart(this, callFrom, barChart, MyAdapter.HourlyData.get(index).getData(), MyAdapter.HourlyData.get(index).getTimeStamp());
    }
}
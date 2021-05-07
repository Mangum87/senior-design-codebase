package com.example.myapplication.footSteps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.chart.PlotChart;
import com.example.myapplication.recyclerView.MyAdapter;
import com.github.mikephil.charting.charts.BarChart;

public class FootStepsMoreSpecificDateClicked extends AppCompatActivity {
    private String callFrom = "footSteps";
    private TextView date, collapseScreen, totalValue;
    private BarChart barChart;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_steps_more_specific_date_clicked);

        date = findViewById(R.id.footStepsSpecificDate);
        totalValue = findViewById(R.id.footStepsSpecificDateTotalValue);
        collapseScreen = findViewById(R.id.collapseFootStepsSpecificDateView);
        barChart = findViewById(R.id.barChartFootStepsSpecificDateScreen);

        getData();
        setValues();

        collapseScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    /**
     * stores the index of data which is sent from previous intent 'Recycler view Adapter' when user clicks specific date
     */
    private void getData() {
        index = getIntent().getIntExtra("index", 1);
    }

    private void setValues() {
        date.setText(MyAdapter.HourlyData.get(index).getDate());
        totalValue.setText(String.valueOf((int) (MyAdapter.HourlyData.get(index).getTotal())));

        PlotChart.barChart(this, callFrom, barChart, MyAdapter.HourlyData.get(index).getData(), MyAdapter.HourlyData.get(index).getTimeStamp());
    }
}
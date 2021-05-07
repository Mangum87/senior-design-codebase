package com.example.myapplication.heartRate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.chart.PlotChart;
import com.example.myapplication.recyclerView.MyAdapter;
import com.github.mikephil.charting.charts.LineChart;

public class HeartRateMoreSpecificDateClicked extends AppCompatActivity {
    TextView date,high,low,collapseScreen;
    LineChart lineChart;

    /**set a flag to check if index is received from previous intent which is passed from 'Recycler View Adapter'
     * without flag index is always initialized '0' and always plots data on index '0' on arrayList
     */
    private boolean hasData;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_specific_date_clicked);

        date = findViewById(R.id.heartRateSpecificDate);
        collapseScreen = findViewById(R.id.collapseHeartRateSpecificDateView);
        high = findViewById(R.id.heartRateSpecificDateHighValue);
        low = findViewById(R.id.heartRateSpecificDateLowValue);
        lineChart = findViewById(R.id.lineChartHeartRateSpecificDateScreen);

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
    private void getData(){
        if(getIntent().hasExtra("index") ){
            index = getIntent().getIntExtra("index",1);
            hasData = true;
        }
        else{
            Toast.makeText(this,"No Data Availble",Toast.LENGTH_SHORT).show();
            hasData = false;
        }
    }

    private void setValues(){
        /** check the flag for index received or not */
        if(hasData){
            date.setText(MyAdapter.HourlyData.get(index).getDate());
            high.setText(String.valueOf(MyAdapter.HourlyData.get(index).getHigh()));
            low.setText(String.valueOf(MyAdapter.HourlyData.get(index).getLow()));

            PlotChart.lineChart(this,false, lineChart, MyAdapter.HourlyData.get(index).getData(), MyAdapter.HourlyData.get(index).getTimeStamp());
        }
    }
}

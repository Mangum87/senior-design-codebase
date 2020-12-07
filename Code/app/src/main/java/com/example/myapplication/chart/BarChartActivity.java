package com.example.myapplication.chart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;

import com.example.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BarChartActivity {

    //TODO: need to pass the data received from the server
    public void plot_barGraph(BarChart b) {
        //Toast.makeText(BarChartActivity.this, "Inside Barchart", Toast.LENGTH_LONG).show();
        BarChart barChart;
        float barWidth, barSpace, groupSpace;
        barWidth = 0.6f;
        barSpace = 0.08f;
        groupSpace = 0.5f;

        barChart = b;
        //barChart.getDescription().setText(String.valueOf(R.string.current_date)); //Todo: need to solve this
        //barChart.getDescription().setTextColor(Color.BLACK);
        barChart.setPinchZoom(false);
        barChart.setScaleEnabled(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);

        int[] data = {86, 0, 85, 79, 76, 76, 77};

        //labels for xAxis
        ArrayList<String> xVals = new ArrayList<>();
        xVals.add(""); //this is to allign the xlabels according to bar
        xVals.add("S");
        xVals.add("M");
        xVals.add("T");
        xVals.add("W");
        xVals.add("Th");
        xVals.add("F");
        xVals.add("SA");

        //set value for yaxis
        ArrayList<BarEntry> yVals = new ArrayList();
        int a = 1;
        for (int i = 0; i < data.length; i++) {

            yVals.add(new BarEntry(a, data[i]));
            a++;
        }

        BarDataSet barDataSet = new BarDataSet(yVals, "");

        ArrayList colors = new ArrayList();

        //color customization of bar graph
        int red = Color.parseColor("#f06262");
        int green = Color.parseColor("#2dc437");

        //customize the color for the graph
        for (int i = 0; i < yVals.size(); i++) {
            if (yVals.get(i).getY() < 80) {
                colors.add(red);
            } else {
                colors.add(green);
            }
        }

        barDataSet.setColors(colors);
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(10f);
        barChart.setDrawValueAboveBar(false); // for bar label to fit inside bar

        BarData barData = new BarData(barDataSet);

        //this is to hide 0 if there is zero values for bar data
        barData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {

                if (value > 0){
                    return super.getFormattedValue((int)value);
                }else{
                    return "";
                }
            }

        });

        barChart.setData(barData);
        barChart.getBarData().setBarWidth(barWidth);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * 7);
        barChart.animateY(1000);
        barChart.getDescription().setEnabled(true);
        barChart.getLegend().setEnabled(false);

        //X-axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setLabelCount(7);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(false);
        xAxis.setAxisMinimum(0f);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

        //Y-axis
        barChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setTextSize(12f);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinimum(0f);

        barChart.invalidate();
    }
}


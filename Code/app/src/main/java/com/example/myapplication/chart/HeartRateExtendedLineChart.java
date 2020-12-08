package com.example.myapplication.chart;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.util.ArrayList;

public class HeartRateExtendedLineChart {

    //TODO: need to pass the data received from the server
    public void plotHeartRateExtendedLineChart(LineChart l){

        LineChart lineChart = l;
        l.setPinchZoom(false);
        l.setScaleEnabled(false);
        l.setDrawGridBackground(false);

        ArrayList yVals = new ArrayList();
        yVals.add(new Entry(1, 8));
        yVals.add(new Entry(2, 7));
        yVals.add(new Entry(3, 6));
        yVals.add(new Entry(4, 10));
        yVals.add(new Entry(5, 9));
        yVals.add(new Entry(6, 8));
        yVals.add(new Entry(7, 9));
        yVals.add(new Entry(8, 8));
        yVals.add(new Entry(9, 8));
        yVals.add(new Entry(10, 7));
        yVals.add(new Entry(11, 8));
        yVals.add(new Entry(12, 10));
        yVals.add(new Entry(13, 9));
        yVals.add(new Entry(14, 8));
        yVals.add(new Entry(15, 9));
        yVals.add(new Entry(16, 8));
        yVals.add(new Entry(17, 7));
        yVals.add(new Entry(18, 6));
        yVals.add(new Entry(19, 9));
        yVals.add(new Entry(20, 9));
        yVals.add(new Entry(21, 8));
        yVals.add(new Entry(22, 9));
        yVals.add(new Entry(23, 6));

        LineDataSet lineDataSet = new LineDataSet(yVals,"Sleep");
        int myColor = Color.parseColor("#80FF85");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setFillAlpha(0);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(Color.CYAN);
        lineDataSet.setColor(myColor);
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setValueTextSize(13f);
        lineDataSet.setLineWidth(4f);

        LineData lineData = new LineData(lineDataSet);
        lineData.setValueFormatter(new LargeValueFormatter());

        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);

        //Customize X-axis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMinimum(0);
        xAxis.setTextSize(14f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setEnabled(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //Customize Y-axis
        lineChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setTextSize(14f);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinimum(0f);

        lineChart.invalidate();
    }
}

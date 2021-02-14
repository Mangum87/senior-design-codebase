package com.example.myapplication.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.util.ArrayList;
import java.util.Vector;

public class HeartRateExtendedLineChart {

    public void plotHeartRateExtendedLineChart(LineChart l, ArrayList<Double> data){

        LineChart lineChart = l;
        lineChart.setPinchZoom(false);
        lineChart.setScaleEnabled(false);
        lineChart.setDrawGridBackground(false);

        //labels for xAxis
        ArrayList<String> xVals = new ArrayList<>();
        xVals.add(""); //this is to allign the xlabels according to bar
        xVals.add("12AM");
        xVals.add("6AM");
        xVals.add("12PM");
        xVals.add("6PM");
        xVals.add("");

        //set Values for yaxis
        ArrayList<Entry> yVals = new ArrayList();
        int a = 1; //counter for the number of barlines

        //adds all data to be plotted into the yvals array
        for (int i = 0; i < data.size(); i++) {
            yVals.add(new Entry(a, data.get(i).intValue()));
            a++;
        }

        //array list for custom color for the graph
        ArrayList colors = new ArrayList();

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //  purpose: color customization of bar graph                                                                                                   ////
        //  note: graph was not using the color from values->colors and limited color on Color library so had to individually assign and parse color    ////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        int red = Color.parseColor("#f06262");
        int green = Color.parseColor("#80FF85");

//        //gradient fill in chart
        Paint paint = lineChart.getRenderer().getPaintRender();
        int height = lineChart.getHeight();

        LinearGradient linGrad = new LinearGradient(0, 0, 0, height,
                red,
                green,
                Shader.TileMode.REPEAT);
        paint.setShader(linGrad);

//        //setting what color which bar needs to be and adding is to colors array 'not working for cubic bezier
//        for (int i = 0; i < yVals.size(); i++) {
//            if (yVals.get(i).getY() <= 70) {
//                colors.add(red);
//            }
//            else if (yVals.get(i).getY() >=71 && yVals.get(i).getY() <= 110 ){
//                colors.add(green);
//            }
//            else if (yVals.get(i).getY() >= 111){
//                colors.add(red);
//            }
//        }

        LineDataSet lineDataSet = new LineDataSet(yVals,"Sleep");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCubicIntensity(0.2f);
//        lineDataSet.setColors(colors);
        lineDataSet.setLineWidth(2f);

        LineData lineData = new LineData(lineDataSet);
        lineData.setValueFormatter(new LargeValueFormatter());

        lineChart.getXAxis().setAxisMinimum(0);
        lineChart.animateX(1200);
        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setExtraRightOffset(2);

        //Customize X-axis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMinimum(0f);
        xAxis.setTextSize(8f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setLabelCount(yVals.size());
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

        //Customize Y-axis
        lineChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setTextSize(8f);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinimum(30); //assuming the heart rate fall below 40bpm

        lineChart.invalidate();
    }
}

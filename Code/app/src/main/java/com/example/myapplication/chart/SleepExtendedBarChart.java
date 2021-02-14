package com.example.myapplication.chart;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class SleepExtendedBarChart {

    //TODO: need to pass the data received from the server
    public static void plotSleepExtendedBarChart(BarChart b) {
        BarChart barChart;
        float barWidth, barSpace, groupSpace;
        barWidth = 0.6f;
        barSpace = 0.08f;
        groupSpace = 0.5f;

        barChart = b;
        barChart.getDescription().setTextColor(Color.BLACK);

        barChart.setPinchZoom(false); //disable the zooming feature of graph
        barChart.setScaleEnabled(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false); // removes grid from the background

        int[] data = {86, 0, 85, 79, 76, 76, 77}; //this the arraylist of data to be plotted

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
        int a = 1; //counter for the number of barlines

        //adds all data to be plotted into the yvals array
        for (int i = 0; i < data.length; i++) {
            yVals.add(new BarEntry(a, data[i]));
            a++;
        }

        BarDataSet barDataSet = new BarDataSet(yVals, "");

        //array list for custom color for the graph
        ArrayList colors = new ArrayList();

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //  purpose: color customization of bar graph                                                                                                   ////
        //  note: graph was not using the color from values->colors and limited color on Color library so had to individually assign and parse color    ////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        int red = Color.parseColor("#f06262");
        int green = Color.parseColor("#2dc437");

        //setting what color which bar needs to be and adding is to colors array
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
        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * 7); // to set the space between the bars so that xlabels allign with graph
        barChart.animateY(1000); // animates the yaxis graph while plotting
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);

        //X-axis customization
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

        //Y-axis customization
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

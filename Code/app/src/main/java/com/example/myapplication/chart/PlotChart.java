package com.example.myapplication.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.SignOut;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class PlotChart {

    /**
     * @param context - to access color defined in values
     * @param callFrom - to filter the data and graphing properties
     * @param barChart - bar chart assigned in xml file
     * @param data - value to be plotted
     * @param xVals - labels for xAxis
     */
    public static void barChart(Context context, String callFrom, BarChart barChart, ArrayList<Double> data , ArrayList<String> xVals) {

        float barWidth = 0.6f;
        xVals.add(0,""); //this is to align the xLabels according to bar

        /** set value for yAxis */
        ArrayList<BarEntry> yVals = new ArrayList();
        int a = 1; //counter for the number of barlines

        /** adds all data to be plotted into the yVals array */
        for (int i = 0; i < data.size(); i++) {
            yVals.add(new BarEntry(a, data.get(i).floatValue()));
            a++;
        }

        BarDataSet barDataSet = new BarDataSet(yVals, "");

        barDataSet.setColor(ContextCompat.getColor(context, R.color.transparent));
        barDataSet.setDrawValues(false); // for values labels on the bar
        BarData barData = new BarData(barDataSet);

        /**this is to hide 0 if there is zero values for bar data */
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

        CustomMarkView mv = new CustomMarkView(context, R.layout.custom_chart_markview);
        barChart.setMarker(mv);
        barChart.setData(barData);
        barChart.getBarData().setBarWidth(barWidth);
        barChart.animateY(1000);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);

        barChart.setVisibleXRangeMaximum(30);

        //X-axis customization
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(false);
        xAxis.setAxisMinimum(0f);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

        //Y-axis customization
        barChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setTextSize(12f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinimum(0f);

        switch (callFrom){
            case "footSteps":
            case "calories":
            case "heartRate":
            case "sleep" :
            case "caloriesBMR":
            case "lightlyActive":
            case "fairlyActive":
            case "veryActive":
                leftAxis.setValueFormatter(new LargeValueFormatter()); //show 1000 labels in '1k' pattern
        }

        barChart.invalidate();
    }

    /**
     * @param context for accessing color
     */
    public static void lineChart(Context context, LineChart lineChart, ArrayList<Double> data, ArrayList<String> xVals){

        xVals.add(0,""); /** this is to align the xLabel according to lines */

        /** set Values for yAxis */
        ArrayList<Entry> yVals = new ArrayList();
        int a = 1;

        /** adds all data to be plotted into the yVals array */
        for (int i = 0; i < data.size(); i++) {
            yVals.add(new Entry(a, data.get(i).intValue()));
            a++;
        }


        LineDataSet lineDataSet = new LineDataSet(yVals,"Sleep");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCubicIntensity(0.2f);
        lineDataSet.setLineWidth(2f);

        LineData lineData = new LineData(lineDataSet);

        /** gradient fill in chart */
        Paint paint = lineChart.getRenderer().getPaintRender();
        int height = lineChart.getHeight();

        int red = Color.parseColor("#f06262");
        int green = Color.parseColor("#80FF85");
        LinearGradient linGrad = new LinearGradient(0, 0, 0, 0,
                red,
                green,
                Shader.TileMode.REPEAT);
        paint.setShader(linGrad);

        //lineChart.getXAxis().setAxisMinimum(0);
        lineChart.animateX(1200);
        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
//        lineChart.setExtraRightOffset(2);

        //Customize X-axis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMinimum(0f);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

        //Customize Y-axis
        lineChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setTextSize(12f);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(20f);

    }
}


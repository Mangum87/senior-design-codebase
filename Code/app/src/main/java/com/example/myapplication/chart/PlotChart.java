package com.example.myapplication.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
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

/** this class has all the graphing charts and its properties */
public class PlotChart {

    /**
     * @param context  - to access color defined in values
     * @param callFrom - to filter the data and graphing properties
     * @param barChart - bar chart assigned in xml file
     * @param data     - value to be plotted
     * @param xVals    - labels for xAxis
     */
    public static void barChart(Context context, String callFrom, BarChart barChart, ArrayList<Double> data, ArrayList<String> xVals) {

        float barWidth = 0.6f;
        xVals.add(0, ""); //this is to align the xLabels according to bar

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

                if (value > 0) {
                    return super.getFormattedValue((int) value);
                } else {
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

        switch (callFrom) {
            case "footSteps":
            case "calories":
            case "heartRate":
            case "sleep":
            case "caloriesBMR":
            case "lightlyActive":
            case "fairlyActive":
            case "veryActive":
                leftAxis.setValueFormatter(new LargeValueFormatter()); //show 1000 labels in '1k' pattern
        }
    }

    /**
     * @param context   to access colors defined in res->values->colors
     * @param callFrom  to set the graphing properties according to the screen
     * @param lineChart chart where the graph is shown
     * @param data      values to be plotted
     * @param xVals     xLables
     */
    public static void lineChart(Context context, String callFrom, LineChart lineChart, ArrayList<Double> data, ArrayList<String> xVals) {

        xVals.add(0, ""); /** this is to align the xLabel according to lines */

        /** set Values for yAxis */
        ArrayList<Entry> yVals = new ArrayList();
        int a = 1;

        /** adds all data to be plotted into the yVals array */
        for (int i = 0; i < data.size(); i++) {
            yVals.add(new Entry(a, data.get(i).intValue()));
            a++;
        }

        LineDataSet lineDataSet = new LineDataSet(yVals, "Sleep");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCubicIntensity(0.2f);
        lineDataSet.setLineWidth(2f);

        LineData lineData = new LineData(lineDataSet);

        /** gradient fill in chart */
        Paint paint = lineChart.getRenderer().getPaintRender();
//        int height = lineChart.getHeight();

        LinearGradient linGrad = new LinearGradient(0, 0, 0, 0,
                ContextCompat.getColor(context, R.color.lightRed),
                ContextCompat.getColor(context, R.color.lightGreen),
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
        /** because main screen background is white */
        if (callFrom.equals("heartRateMain")) {
            xAxis.setTextColor(Color.BLACK);
        } else {
            xAxis.setTextColor(Color.WHITE);
        }
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

        //Customize Y-axis
        lineChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = lineChart.getAxisLeft();
        /** because main screen background is white */
        if (callFrom.equals("heartRateMain")) {
            leftAxis.setTextColor(Color.BLACK);
        } else {
            leftAxis.setTextColor(Color.WHITE);
        }
        leftAxis.setTextSize(12f);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(20f);

    }

    public static void pieChart(Context context, String callFrom, int index, PieChart pieChart) {
//        ArrayList<PieEntry> yVals = new ArrayList<>();
//        ArrayList<String> xVals = new ArrayList<>();
//        yVals.add(new PieEntry(ReadSleepData.allSleepData.get(index).getTotalDeep(), "Deep"));
//        yVals.add(new PieEntry(ReadSleepData.allSleepData.get(index).getTotalRem(), "Rem"));
//        yVals.add(new PieEntry(ReadSleepData.allSleepData.get(index).getTotalLight(), "Light"));
//        yVals.add(new PieEntry(ReadSleepData.allSleepData.get(index).getTotalWake(), "Wake"));
//
//        ArrayList<Integer> colors = new ArrayList<>();
//        colors.add(ContextCompat.getColor(context, R.color.red));
//        colors.add(ContextCompat.getColor(context, R.color.green));
//        colors.add(ContextCompat.getColor(context, R.color.light_blue));
//        colors.add(ContextCompat.getColor(context, R.color.endblue));
//
//
////        xVals.add("Deep");
////        xVals.add("Rem");
////        xVals.add("Light");
////        xVals.add("Wake");
//
//        pieChart.setDrawHoleEnabled(true);
//        pieChart.setHoleColor(Color.TRANSPARENT);
//        pieChart.setHoleRadius(7);
//        pieChart.setTransparentCircleRadius(10);
//
//        pieChart.setRotation(0);
//        pieChart.setRotationEnabled(true);
//
//        PieDataSet dataSet = new PieDataSet(yVals, "Sleep");
//        dataSet.setValueTextSize(12f);
//        dataSet.setColors(colors);
//        dataSet.setSliceSpace(3);
//        dataSet.setSelectionShift(5);
//
//        PieData pieData = new PieData(dataSet);
//        pieData.setDrawValues(true);
//
//        pieChart.setData(pieData);
//        pieChart.invalidate();

    }
}


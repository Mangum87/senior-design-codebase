package com.example.myapplication.chart;

import android.content.Context;
import android.widget.TextView;

import com.example.myapplication.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class CustomMarkView extends MarkerView {

    private TextView tvContent, tvHr, tvMin;
    private boolean forSleepPieChart, forActiveChart;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public CustomMarkView(Context context, int layoutResource, boolean forSleepPieChart, boolean forActiveChart) {
        super(context, layoutResource);

        this.forSleepPieChart = forSleepPieChart;
        this.forActiveChart = forActiveChart;

        // sleep has hr and min so it different from other mark view
        if(forSleepPieChart || forActiveChart){
            tvHr = (TextView) findViewById(R.id.tvContentHr);
            tvMin = (TextView) findViewById(R.id.tvContentMin);
        }
        else{
            // find layout components
            tvContent = (TextView) findViewById(R.id.tvContent);
        }
    }

    /** callbacks everytime the MarkerView is redrawn, can be used to update the content (user-interface) */
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if(forSleepPieChart){
            int totSec = (int) e.getY();

            int hour = totSec / 60;
            int min = hour % 60;
            hour = hour / 60;

            tvHr.setText("" + hour);
            tvMin.setText("" + min);
        }
        else if(forActiveChart){
            int totalMin = (int) e.getY();

            int hour = totalMin / 60;
            int min = totalMin % 60;

            tvHr.setText("" + hour);
            tvMin.setText("" + min);
        }
        else{
            tvContent.setText("" + (int) e.getY());
        }

        // this will perform necessary layouting
        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {

        if(mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }
        return mOffset;
    }
}




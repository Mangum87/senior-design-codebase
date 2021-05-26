package com.example.myapplication.recyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.readAndSaveAllFile.Hourly.HourlyActiveDataSets;
import com.example.myapplication.readAndSaveAllFile.Hourly.HourlyIndividualDataSets;
import com.example.myapplication.readAndSaveAllFile.Sleep.SleepFile;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Activity activity; // to set the animation transition
    private Context context;
    private String callFrom;

    public static ArrayList<HourlyActiveDataSets> HourlyAllActiveData = new ArrayList<>();
    public static ArrayList<SleepFile> SleepData = new ArrayList<>();
    public static ArrayList<HourlyIndividualDataSets> HourlyData =  new ArrayList<>();;

    //active
    public MyAdapter(ArrayList<HourlyActiveDataSets> HourlyAllActiveData, Context context, String callFrom) {
        this.HourlyAllActiveData = HourlyAllActiveData;
        this.context = context;
        this.callFrom = callFrom;
        this.activity = (Activity) context;
    }

    //sleep
    public MyAdapter(Context context, String callFrom, ArrayList<SleepFile> SleepData) {
        this.context = context;
        this.callFrom = callFrom;
        this.SleepData = SleepData;
        this.activity = (Activity) context;
    }

    //hourly except active
    public MyAdapter(String callFrom, Context context, ArrayList<HourlyIndividualDataSets> HourlyData ) {
        this.context = context;
        this.callFrom = callFrom;
        this.HourlyData = HourlyData;
        this.activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = null;

        switch (callFrom) {
            case "footSteps":
                view = inflater.inflate(R.layout.recycler_view_foot_steps_more, parent, false);
                break;
            case "miles":
                view = inflater.inflate(R.layout.recycler_view_miles_more, parent, false);
                break;
            case "calories":
                view = inflater.inflate(R.layout.recycler_view_calories_more, parent, false);
                break;
            case "heartRate":
                view = inflater.inflate(R.layout.recycler_view_heart_rate_more, parent, false);
                break;
            case "sleep":
                view = inflater.inflate(R.layout.recycler_view_sleep_more, parent, false);
                break;
            case "active":
                view = inflater.inflate(R.layout.recycler_view_active_more, parent, false);
                break;
        }

        return new MyAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        switch (callFrom) {
            case "footSteps":
                holder.dateValue.setText(HourlyData.get(position).getDate());
                holder.totalValueFootSteps.setText(String.valueOf((int) HourlyData.get(position).getTotal())); //casted to int to remove the decimal part from the average value
                break;
            case "miles":
                holder.dateValue.setText(HourlyData.get(position).getDate());
                holder.totalValueMiles.setText(String.valueOf(HourlyData.get(position).getTotal()));
                break;
            case "calories":
                holder.dateValue.setText(HourlyData.get(position).getDate());
                holder.totalValueCalories.setText(String.valueOf((int) HourlyData.get(position).getTotal()));
                break;
            case "heartRate":
                holder.dateValue.setText(HourlyData.get(position).getDate());
                holder.averageValueHR.setText(String.valueOf((int) HourlyData.get(position).getAverage()));
                break;
            case "sleep":
                holder.dateValue.setText(SleepData.get(position).getDate());
                holder.totalSleepHr.setText(String.valueOf(SleepData.get(position).getTotalHoursSlept()));
                holder.totalSleepMin.setText(String.valueOf(SleepData.get(position).getTotalMinuteSlept()));
                break;
            case "active":
                holder.dateValue.setText(HourlyAllActiveData.get(position).getDate());
                holder.totalActiveHr.setText(String.valueOf(HourlyAllActiveData.get(position).getHrActive()));
                holder.totalActiveMin.setText(String.valueOf(HourlyAllActiveData.get(position).getMinActive()));
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                switch (callFrom) {
                    case "footSteps":
                        intent = new Intent(context, com.example.myapplication.footSteps.FootStepsMoreSpecificDateClicked.class);
                        intent.putExtra("index", position);
                        break;
                    case "miles":
                        intent = new Intent(context, com.example.myapplication.miles.MilesMoreSpecificDateClicked.class);
                        intent.putExtra("index", position);
                        break;
                    case "calories":
                        intent = new Intent(context, com.example.myapplication.calories.CaloriesMoreSpecificDateClicked.class);
                        intent.putExtra("index", position);
                        break;
                    case "heartRate":
                        intent = new Intent(context, com.example.myapplication.heartRate.HeartRateMoreSpecificDateClicked.class);
                        intent.putExtra("index", position);
                        break;
                    case "sleep":
                        intent = new Intent(context, com.example.myapplication.sleep.SleepMoreSpecificDateClicked.class);
                        intent.putExtra("index", position);
                        break;
                    case "active":
                        intent = new Intent(context, com.example.myapplication.active.ActiveMoreSpecificDateClicked.class);
                        intent.putExtra("index", position);
                        break;
                }
                context.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(callFrom == "sleep"){
            return SleepData.size();
        }
        else if(callFrom == "active"){
            return HourlyAllActiveData.size();
        }
        else{
            return HourlyData.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dateValue, totalValueFootSteps, totalValueMiles, totalValueCalories, averageValueHR, totalSleepHr, totalSleepMin, totalActiveHr, totalActiveMin;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            switch (callFrom) {
                case "footSteps":
                    dateValue = itemView.findViewById(R.id.footStepsMoreRecyclerViewDate);
                    totalValueFootSteps = itemView.findViewById(R.id.footStepsMoreRecyclerViewTotalValue);
                    break;
                case "miles":
                    dateValue = itemView.findViewById(R.id.milesMoreRecyclerViewDate);
                    totalValueMiles = itemView.findViewById(R.id.milesMoreRecyclerViewTotalValue);
                    break;
                case "calories":
                    dateValue = itemView.findViewById(R.id.caloriesMoreRecyclerViewDate);
                    totalValueCalories = itemView.findViewById(R.id.caloriesMoreRecyclerViewTotalValue);
                    break;
                case "heartRate":
                    dateValue = itemView.findViewById(R.id.heartRateMoreRecyclerViewDate);
                    averageValueHR = itemView.findViewById(R.id.heartRateMoreRecyclerViewAvgValue);
                    break;
                case "sleep":
                    dateValue = itemView.findViewById(R.id.sleepMoreRecyclerViewDate);
                    totalSleepHr = itemView.findViewById(R.id.sleepMoreRecyclerViewHrs);
                    totalSleepMin = itemView.findViewById(R.id.sleepMoreRecyclerViewMin);
                    break;
                case "active":
                    dateValue = itemView.findViewById(R.id.activeMoreRecyclerViewDate);
                    totalActiveHr = itemView.findViewById(R.id.activeMoreRecyclerViewHrs);
                    totalActiveMin = itemView.findViewById(R.id.activeMoreRecyclerViewMin);
                    break;
            }
        }
    }
}

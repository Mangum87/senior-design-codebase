package com.example.myapplication.recyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.readAndSaveAllFile.CalculateData;
import com.example.myapplication.readAndSaveAllFile.MultipleFileData;
import com.example.myapplication.readAndSaveAllFile.ReadAndSaveMultipleFile;
import com.example.myapplication.readAndSaveAllFile.Sleep.SleepFileManager;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private Activity activity; // to set the animation transition
    private Context context;
    private String callFrom;
    private SleepFileManager sleepFileManager;

    public MyAdapter(Context context, String callFrom) {
        this.context = context;
        this.callFrom = callFrom;
        this.activity = (Activity) context;
    }

    public MyAdapter(Context context,String callFrom,SleepFileManager sleepFileManager){
        this.context = context;
        this.callFrom = callFrom;
        this.sleepFileManager = sleepFileManager;
        this.activity = (Activity) context;
    }


    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = null;

        switch (callFrom){
            case "footSteps":
                view = inflater.inflate(R.layout.recycler_view_foot_steps_more, parent, false);
                break;
            case "miles":
                view = inflater.inflate(R.layout.recycler_view_miles_more, parent, false);
                break;
            case "calories":
                view = inflater.inflate(R.layout.recycler_view_calories_more, parent,false);
                break;
            case "heartRate":
                view = inflater.inflate(R.layout.recycler_view_heart_rate_more, parent, false);
                break;
            case "sleep":
                view = inflater.inflate(R.layout.recycler_view_sleep_more, parent, false);
                break;
            case "caloriesBMR":
                break;
            case "lightlyActive":
                break;
            case "fairlyActive":
                break;
            case "veryActive":
                break;
        }

        return new MyAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        int totValue = 0;
        switch (callFrom){
            case "footSteps":
                holder.dateValue.setText(ReadAndSaveMultipleFile.allData.get(position).getDate());
                totValue= (int) CalculateData.getTotal(ReadAndSaveMultipleFile.allData.get(position).getSteps()); //casted to int to remove the decimal part from the average value
                holder.totalValueFootSteps.setText(String.valueOf(totValue));
                break;
            case "miles":
                holder.dateValue.setText(ReadAndSaveMultipleFile.allData.get(position).getDate());
                holder.totalValueMiles.setText(String.valueOf(CalculateData.getTotal(ReadAndSaveMultipleFile.allData.get(position).getDistance())));
                break;
            case "calories":
                holder.dateValue.setText(ReadAndSaveMultipleFile.allData.get(position).getDate());
                totValue = (int) CalculateData.getTotal(ReadAndSaveMultipleFile.allData.get(position).getCalories());
                holder.totalValueCalories.setText(String.valueOf(totValue));
                break;
            case "heartRate":
                holder.dateValue.setText(ReadAndSaveMultipleFile.allData.get(position).getDate());
                int avgValue = (int) CalculateData.getAverage(ReadAndSaveMultipleFile.allData.get(position).getHeartRate());
                holder.averageValueHR.setText(String.valueOf(avgValue));
                break;
            case "sleep":
                holder.dateValue.setText(SleepFileManager.files.get(position).getDate());
                holder.totalSleepHr.setText(String.valueOf(SleepFileManager.files.get(position).getTotalHoursSlept()));
                holder.totalSleepMin.setText(String.valueOf(SleepFileManager.files.get(position).getTotalMinuteSlept()));
                break;
            case "caloriesBMR":
                break;
            case "lightlyActive":
                break;
            case "fairlyActive":
                break;
            case "veryActive":
                break;
        }

        holder.moreArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                switch (callFrom){
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
                        intent = new Intent(context,com.example.myapplication.sleep.SleepMoreSpecificDateClicked.class);
                        intent.putExtra("index", position);
                        break;
                    case "caloriesBMR":
                        break;
                    case "lightlyActive":
                        break;
                    case "fairlyActive":
                        break;
                    case "veryActive":
                        break;
                }
                context.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ReadAndSaveMultipleFile.allData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dateValue, totalValueFootSteps, totalValueMiles, totalValueCalories, moreArrow, averageValueHR, totalSleepHr, totalSleepMin;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            switch (callFrom){
                case "footSteps":
                    dateValue = itemView.findViewById(R.id.footStepsMoreRecyclerViewDate);
                    totalValueFootSteps = itemView.findViewById(R.id.footStepsMoreRecyclerViewTotalValue);
                    moreArrow = itemView.findViewById(R.id.footStepsMoreRecyclerViewSelectedDate);
                    break;
                case "miles":
                    dateValue = itemView.findViewById(R.id.milesMoreRecyclerViewDate);
                    totalValueMiles = itemView.findViewById(R.id.milesMoreRecyclerViewTotalValue);
                    moreArrow = itemView.findViewById(R.id.milesMoreRecyclerViewSelectedDate);
                    break;
                case "calories":
                    dateValue = itemView.findViewById(R.id.caloriesMoreRecyclerViewDate);
                    totalValueCalories = itemView.findViewById(R.id.caloriesMoreRecyclerViewTotalValue);
                    moreArrow = itemView.findViewById(R.id.caloriesMoreRecyclerViewSelectedDate);
                    break;
                case "heartRate":
                    dateValue = itemView.findViewById(R.id.heartRateMoreRecyclerViewDate);
                    averageValueHR = itemView.findViewById(R.id.heartRateMoreRecyclerViewAvgValue);
                    moreArrow = itemView.findViewById(R.id.heartRateMoreRecyclerViewSelectedDate);
                    break;
                case "sleep":
                    dateValue = itemView.findViewById(R.id.sleepMoreRecyclerViewDate);
                    totalSleepHr = itemView.findViewById(R.id.sleepMoreRecyclerViewHrs);
                    totalSleepMin = itemView.findViewById(R.id.sleepMoreRecyclerViewMin);
                    moreArrow = itemView.findViewById(R.id.sleepMoreRecyclerViewSelectedDate);
                    break;
                case "caloriesBMR":
                    break;
                case "lightlyActive":
                    break;
                case "fairlyActive":
                    break;
                case "veryActive":
                    break;
            }
        }
    }
}

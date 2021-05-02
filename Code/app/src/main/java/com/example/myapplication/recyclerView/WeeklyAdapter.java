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
import com.example.myapplication.readAndSaveAllFile.ReadAndSaveMultipleFile;
import com.example.myapplication.readAndSaveAllFile.Sleep.SleepFileManager;

import java.util.ArrayList;

public class WeeklyAdapter extends RecyclerView.Adapter<WeeklyAdapter.MyViewHolder>{
    private Activity activity; // to set the animation transition
    private Context context;
    private String callFrom;

    private ArrayList<Double> dayHeartAvg = new ArrayList<>();
    private ArrayList<Integer> daySleepHrAvg = new ArrayList<>();
    private ArrayList<Integer> daySleepMinAvg = new ArrayList<>();
    private ArrayList<Integer> dayActiveHrAvg = new ArrayList<>();
    private ArrayList<Integer> dayActiveMinAvg = new ArrayList<>();
    private ArrayList<String> dayDates = new ArrayList<>();
    private ArrayList<String> sleepDates = new ArrayList<>();

    private ArrayList<Double> weekHeartAvg = new ArrayList<>();
    private ArrayList<Double> weekSleepHrAvg = new ArrayList<>();
    private ArrayList<Double> weekSleepMinAvg = new ArrayList<>();
    private ArrayList<Integer> weekActiveHrAvg = new ArrayList<>();
    private ArrayList<Integer> weekActiveMinAvg = new ArrayList<>();
    private ArrayList<String> weekDates = new ArrayList<>();
    private ArrayList<String> weekSleepDates = new ArrayList<>();

    public WeeklyAdapter(Context context, String callFrom) {
        this.context = context;
        this.callFrom = callFrom;
        this.activity = (Activity) context;
    }

    @NonNull
    @Override
    public WeeklyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = null;

        switch (callFrom){
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

        return new WeeklyAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyAdapter.MyViewHolder holder, int position) {
        getWeeklyData();
        getWeeklySleepData();

        switch (callFrom){
            case "heartRate":
                holder.dateValue.setText(weekDates.get(position));
                holder.averageValueHR.setText(String.valueOf(weekHeartAvg.get(position).intValue()));
                break;
            case "sleep":
                holder.dateValue.setText(weekSleepDates.get(position));
                holder.totalSleepHr.setText(String.valueOf(weekSleepHrAvg.get(position).intValue()));
                holder.totalSleepMin.setText(String.valueOf(weekSleepMinAvg.get(position).intValue()));
                break;
            case "active":
                holder.dateValue.setText(weekDates.get(position));
                holder.totalActiveHr.setText(String.valueOf((int) weekActiveHrAvg.get(position)));
                holder.totalActiveMin.setText(String.valueOf((int) weekActiveMinAvg.get(position)));
                break;
        }

        holder.moreArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                switch (callFrom){
                    case "heartRate":
                        intent = new Intent(context, com.example.myapplication.heartRate.HeartRateWeeklySpecificDateClicked.class);
                        intent.putExtra("index", position);
                        break;
                    case "sleep":
                        intent = new Intent(context,com.example.myapplication.sleep.SleepWeeklySpecificDateClicked.class);
                        intent.putExtra("index", position);
                        break;
                    case "active":
                        intent = new Intent(context,com.example.myapplication.active.ActiveWeeklySpecificDateClicked.class);
                        intent.putExtra("index", position);
                        break;
                }
                context.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(ReadAndSaveMultipleFile.allData.size()%7 == 0) {
            return ReadAndSaveMultipleFile.allData.size()/7;
        }

        else {
            return (ReadAndSaveMultipleFile.allData.size() - ReadAndSaveMultipleFile.allData.size()%7)/7;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dateValue, moreArrow, averageValueHR, totalSleepHr, totalSleepMin, totalActiveHr, totalActiveMin;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            switch (callFrom){
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
                case "active":
                    dateValue = itemView.findViewById(R.id.activeMoreRecyclerViewDate);
                    totalActiveHr = itemView.findViewById(R.id.activeMoreRecyclerViewHrs);
                    totalActiveMin = itemView.findViewById(R.id.activeMoreRecyclerViewMin);
                    moreArrow = itemView.findViewById(R.id.activeMoreRecyclerViewSelectedDate);
                    break;
            }
        }
    }

    private void getWeeklyData() {

        /** subtracts the remainder if the number of files is not a multiple of 7 */
        int daysForAverage = ReadAndSaveMultipleFile.allData.size() - ReadAndSaveMultipleFile.allData.size()%7;
        double sevenHeartAvg = 0;
        int sevenActiveHrAvg = 0;
        int sevenActiveMinAvg = 0;

        /** gets the data and stores them into the arrays */
        for(int i = 0; i < daysForAverage; i++) {
            dayHeartAvg.add(ReadAndSaveMultipleFile.allData.get(i).getAverageHeartRate());
            dayActiveHrAvg.add(ReadAndSaveMultipleFile.allData.get(i).getHrActive());
            dayActiveMinAvg.add(ReadAndSaveMultipleFile.allData.get(i).getMinActive());
            dayDates.add(ReadAndSaveMultipleFile.allData.get(i).getDate().substring(5)); /** only taking month and day excluding year */
        }

        /** gets the average heart rate and active hours for the week, then adds it to the array list before moving onto the next week */
        for(int i = 0; i < dayDates.size(); i=i+7) {
            for(int j = i; j < i+7; j++) {
                sevenHeartAvg = sevenHeartAvg + dayHeartAvg.get(j);
                sevenActiveHrAvg = sevenActiveHrAvg + dayActiveHrAvg.get(j);
                sevenActiveMinAvg = sevenActiveMinAvg + dayActiveMinAvg.get(j);
            }

            sevenHeartAvg = sevenHeartAvg/7;
            sevenActiveHrAvg = sevenActiveHrAvg/7;
            sevenActiveMinAvg = sevenActiveMinAvg/7;

            weekHeartAvg.add(sevenHeartAvg);
            weekActiveHrAvg.add(sevenActiveHrAvg);
            weekActiveMinAvg.add(sevenActiveMinAvg);
            weekDates.add(dayDates.get(i+6)+" to "+dayDates.get(i));

            /** resetting the variable for the average heart rate and active hours calculation for the next week */
            sevenHeartAvg = 0;
            sevenActiveHrAvg = 0;
            sevenActiveMinAvg = 0;
        }
    }

    private void getWeeklySleepData() {

        /** subtracts the remainder if the number of files is not a multiple of 7 */
        int daysForAverage = SleepFileManager.files.size() - SleepFileManager.files.size()%7;
        double sevenSleepHrAvg = 0;
        double sevenSleepMinAvg = 0;

        for(int i = 0; i < daysForAverage; i++) {
            daySleepHrAvg.add(SleepFileManager.files.get(i).getTotalHoursSlept());
            daySleepMinAvg.add(SleepFileManager.files.get(i).getTotalMinuteSlept());
            sleepDates.add(ReadAndSaveMultipleFile.allData.get(i).getDate().substring(5)); /** only taking month and day excluding year */
        }

        /** gets the average hours of sleep for the week, then adds it to the array list before moving onto the next week */
        for(int i = 0; i < sleepDates.size(); i=i+7) {
            for(int j = i; j < i+7; j++) {
                sevenSleepHrAvg = sevenSleepHrAvg + daySleepHrAvg.get(j);
                sevenSleepMinAvg = sevenSleepMinAvg + daySleepMinAvg.get(j);
            }

            sevenSleepHrAvg = sevenSleepHrAvg/7;
            sevenSleepMinAvg = sevenSleepMinAvg/7;

            weekSleepHrAvg.add(sevenSleepHrAvg);
            weekSleepMinAvg.add(sevenSleepMinAvg);
            weekSleepDates.add(dayDates.get(i+6)+" to "+dayDates.get(i));

            /** resetting the variable for the average hours of sleep calculation for the next week */
            sevenSleepHrAvg = 0;
            sevenSleepMinAvg = 0;
        }
    }
}

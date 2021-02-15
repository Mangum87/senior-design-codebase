package com.example.myapplication.mainScreen;

import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.R;
import com.example.myapplication.calories.CaloriesMore;
import com.example.myapplication.chart.HeartRateExtendedLineChart;
import com.example.myapplication.chart.SleepExtendedBarChart;
import com.example.myapplication.footSteps.FootStepsMore;
import com.example.myapplication.heartRate.HeartRateMore;
import com.example.myapplication.miles.MilesMore;
import com.example.myapplication.readAndSaveAllFile.CalculateData;
import com.example.myapplication.readAndSaveAllFile.MultipleFileData;
import com.example.myapplication.readAndSaveAllFile.ReadAndSaveMultipleFile;
import com.example.myapplication.sleep.SleepMore;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

public class MainScreen extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private View view;
    private Boolean firstCall = true; //avoids duplicate data from storing in 'allData'
    private SwipeRefreshLayout refresh;
    private int Progress = 0;

    //initialize all values of homeScreen
    private TextView valueFootSteps, valueMiles, valueCalories, valueHeartRate, valueSleep, valueCaloriesBMR, valueLightlyActive, valueFairlyActive, valueVeryActive;
    LineChart lineChartHeart;
    BarChart barChartSleep;
    CardView cardViewHeart, cardViewSleep, cardViewCaloriesBMR, cardViewLightlyActive, cardViewFairlyActive, cardViewVeryActive;
    ProgressBar progressBarFootSteps, progressBarMiles, progressBarCalories;
    int indexOfTodaysData = 0; // since the most recent data is stored in index '0'

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mainscreen, container, false);

        /** ///////////////////////////////////////////////////////////////////
         //  initialize the refresh layout and set clickListener             //
         ///////////////////////////////////////////////////////////////////*/
        refresh = view.findViewById(R.id.pullTORefreshLayout);
        refresh.setOnRefreshListener(this);
        /** //////////////////////////////////////////////////////////////////*/

        /** ///////////////////////////////////////////////////////////////////
         //   initialize today cardView attributes                           //
         ///////////////////////////////////////////////////////////////////*/
        valueFootSteps = view.findViewById(R.id.valueFootStepTodayCard);
        valueMiles = view.findViewById(R.id.valueMilesTodayCard);
        valueCalories = view.findViewById(R.id.valueCaloriesTodayCard);
        progressBarFootSteps = view.findViewById(R.id.footStepsProgress);
        progressBarMiles = view.findViewById(R.id.milesProgress);
        progressBarCalories = view.findViewById(R.id.caloriesProgress);

        valueFootSteps.setOnClickListener(this);
        view.findViewById(R.id.textFootStepsTodayCard).setOnClickListener(this);
        valueMiles.setOnClickListener(this);
        view.findViewById(R.id.textMilesTodayCard).setOnClickListener(this);
        valueCalories.setOnClickListener(this);
        view.findViewById(R.id.textCaloriesTodayCard).setOnClickListener(this);
        /** //////////////////////////////////////////////////////////////////*/

        /** ///////////////////////////////////////////////////////////////////
         //   initialize HeartRate cardView attributes                       //
         ///////////////////////////////////////////////////////////////////*/
        cardViewHeart = view.findViewById(R.id.hearRateCardView);
        valueHeartRate = view.findViewById(R.id.valueHeartRateCard);
        lineChartHeart = view.findViewById(R.id.lineChartHeartRateHomeScreen);

        view.findViewById(R.id.textHeartRate).setOnClickListener(this);
        view.findViewById(R.id.textHeartRateMore).setOnClickListener(this);
        /** //////////////////////////////////////////////////////////////////*/

        /** ///////////////////////////////////////////////////////////////////
         //   initialize Sleep cardView attributes                           //
         ///////////////////////////////////////////////////////////////////*/
        cardViewSleep = view.findViewById(R.id.sleepCardView);
        valueSleep = view.findViewById(R.id.valueSleepCard);
        barChartSleep = view.findViewById(R.id.barChartSleepHomeScreen);

        view.findViewById(R.id.textSleep).setOnClickListener(this);
        view.findViewById(R.id.textSleepMore).setOnClickListener(this);
        /** //////////////////////////////////////////////////////////////////*/

        /** ///////////////////////////////////////////////////////////////////
         //   initialize Calories BMR cardView attributes                    //
         ///////////////////////////////////////////////////////////////////*/
        cardViewCaloriesBMR = view.findViewById(R.id.caloriesBmrCardView);
        valueCaloriesBMR = view.findViewById(R.id.valueCaloriesBmr);

        view.findViewById(R.id.textCaloriesBmr).setOnClickListener(this);
        view.findViewById(R.id.textCaloriesBmrMore).setOnClickListener(this);
        /** //////////////////////////////////////////////////////////////////*/

        /** ///////////////////////////////////////////////////////////////////
         //   initialize Lightly Active cardView attributes                  //
         ///////////////////////////////////////////////////////////////////*/
        cardViewLightlyActive = view.findViewById(R.id.lightlyActiveCardView);
        valueLightlyActive = view.findViewById(R.id.valueLightlyActive);

        view.findViewById(R.id.textLightlyActive).setOnClickListener(this);
        view.findViewById(R.id.textLightlyActiveMore).setOnClickListener(this);
        /** //////////////////////////////////////////////////////////////////*/

        /** ///////////////////////////////////////////////////////////////////
         //   initialize Fairly Active cardView attributes                   //
         ///////////////////////////////////////////////////////////////////*/
        cardViewFairlyActive = view.findViewById(R.id.fairlyActiveCardView);
        valueFairlyActive = view.findViewById(R.id.valueFairlyActive);

        view.findViewById(R.id.textFairlyActive).setOnClickListener(this);
        view.findViewById(R.id.textFairlyActiveMore).setOnClickListener(this);
        /** //////////////////////////////////////////////////////////////////*/

        /** ///////////////////////////////////////////////////////////////////
         //   initialize Very Active cardView attributes                     //
         ///////////////////////////////////////////////////////////////////*/
        cardViewVeryActive = view.findViewById(R.id.veryActiveCardView);
        valueVeryActive = view.findViewById(R.id.valueVeryActive);

        view.findViewById(R.id.textVeryActive).setOnClickListener(this);
        view.findViewById(R.id.textVeryActiveMore).setOnClickListener(this);
        /** //////////////////////////////////////////////////////////////////*/

        // only load new data when user refreshes, so set a flag to track the first call which is when after login
        if(firstCall){
            getAllData();
            firstCall = false;
        }

        //when user sees homeScreen, it check if data is stored or not before setting
        if (ReadAndSaveMultipleFile.hasData) {
            showTodaysData();
        }
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getAllData() {
        ReadAndSaveMultipleFile readAndSaveMultipleFile = new ReadAndSaveMultipleFile(view.getContext());
        readAndSaveMultipleFile.readAllFilesName();
    }

    private void updateFootStepsProgress() {
        int totalSteps = (int) CalculateData.getTotal(ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getSteps());
        double totalMiles = CalculateData.getTotal(ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getDistance());
        int totalCalories = (int) CalculateData.getTotal(ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getCalories());

        if(totalSteps <= 6000){
            int progressValue = (int) (((double) totalSteps/6000) * 100);
            ObjectAnimator.ofInt(progressBarFootSteps, "progress", progressValue)
                    .setDuration(900)
                    .start();
        }

        if(totalSteps >= 6000){
            ObjectAnimator.ofInt(progressBarFootSteps, "progress", 100)
                    .setDuration(900)
                    .start();
        }

        if(totalMiles <= 2){
            int progressValue = (int)((totalMiles/2) * 100);
            ObjectAnimator.ofInt(progressBarMiles, "progress", progressValue)
                    .setDuration(900)
                    .start();
        }

        if(totalMiles >= 2){
            ObjectAnimator.ofInt(progressBarMiles, "progress", 100)
                    .setDuration(900)
                    .start();
        }

        if(totalCalories <= 2000){
            int progressValue = (int) (((double) totalCalories/2000) * 100);
            ObjectAnimator.ofInt(progressBarCalories, "progress", progressValue)
                    .setDuration(900)
                    .start();
        }

        if(totalCalories >= 2000){
            ObjectAnimator.ofInt(progressBarCalories, "progress", 100)
                    .setDuration(900)
                    .start();
        }
    }

    private void showTodaysData() {

        updateFootStepsProgress();

        valueFootSteps.setText(String.valueOf((int) CalculateData.getTotal(ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getSteps())));

        valueMiles.setText(String.valueOf(CalculateData.getTotal(ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getDistance())));

        valueCalories.setText(String.valueOf((int) CalculateData.getTotal(ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getCalories())));

        valueHeartRate.setText("89");

        valueCaloriesBMR.setText(String.valueOf((int) CalculateData.getTotal(ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getCaloriesBMR())));

        valueLightlyActive.setText(String.valueOf(CalculateData.getTotal(ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getMinutesLightlyActive())));

        valueFairlyActive.setText(String.valueOf(CalculateData.getTotal(ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getMinutesFairlyActive())));

        valueVeryActive.setText(String.valueOf(CalculateData.getTotal(ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getMinutesVeryActive())));
    }

    /** takes to screen where user can find more detail information about FootSteps till date */
    private void showFootStepsMoreFragment() {
        Fragment fragment = new FootStepsMore();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //two animation first two for when switching from current to new, last two for returning anim back to current fragment from new
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    /** takes to screen where user can find more detail information about Miles Walked  till date */
    private void showMilesMoreFragment() {
        Fragment fragment = new MilesMore();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //two animation first two for when switching from current to new, last two for returning anim back to current fragment from new
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    /** takes to screen where user can find more detail information about calories till date */
    private void showCaloriesMoreFragment() {
        Fragment fragment = new CaloriesMore();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }


    /** extends and makes graph visible for heartRate cardView whose initial visibility is in state 'gone' */
    private void extendHeartRateCardView() {
        if (lineChartHeart.getVisibility() == View.GONE) {
            //initialize extended heartRate chart class
            HeartRateExtendedLineChart chart = new HeartRateExtendedLineChart();
            if(ReadAndSaveMultipleFile.hasData){
                chart.plotHeartRateExtendedLineChart(lineChartHeart, ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getHeartRate()); //plots graph using most recent data
            }
            TransitionManager.beginDelayedTransition(cardViewSleep, new AutoTransition());
            lineChartHeart.setVisibility(View.VISIBLE);
        } else {
            lineChartHeart.setVisibility(View.GONE);
        }
    }


    /** takes to screen where user can find more detail information about HeartRate Activity */
    private void showHeartRateMoreFragment() {
        Fragment fragment = new HeartRateMore();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

    }

    /** this method extends the line chart for sleep card view */
    private void extendSleepCardView() {
        if (barChartSleep.getVisibility() == View.GONE) {
            if(ReadAndSaveMultipleFile.hasData) {
                SleepExtendedBarChart.plotSleepExtendedBarChart(barChartSleep);
            }
            TransitionManager.beginDelayedTransition(cardViewHeart, new AutoTransition());
            barChartSleep.setVisibility(View.VISIBLE);
        } else {
            barChartSleep.setVisibility(View.GONE);
        }
    }

    private void showSleepMoreFragment() {
        Fragment fragment = new SleepMore();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.valueFootStepTodayCard:
            case R.id.textFootStepsTodayCard:
                showFootStepsMoreFragment();
                break;
            case R.id.valueMilesTodayCard:
            case R.id.textMilesTodayCard:
                showMilesMoreFragment();
                break;
            case R.id.valueCaloriesTodayCard:
            case R.id.textCaloriesTodayCard:
                showCaloriesMoreFragment();
                break;
            case R.id.textHeartRate:
                extendHeartRateCardView();
                break;
            case R.id.textHeartRateMore:
                showHeartRateMoreFragment();
                break;
            case R.id.textSleep:
                extendSleepCardView();
                break;
            case R.id.textSleepMore:
                showSleepMoreFragment();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onRefresh() {
        ReadAndSaveMultipleFile.allData.clear();
        getAllData();
        if (ReadAndSaveMultipleFile.hasData) { //checking if there is data or not
            showTodaysData();
        }
        refresh.setRefreshing(false);
    }
}
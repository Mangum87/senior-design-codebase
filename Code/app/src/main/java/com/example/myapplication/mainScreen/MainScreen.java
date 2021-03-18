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
import com.example.myapplication.active.ActiveMore;
import com.example.myapplication.calories.CaloriesMore;
import com.example.myapplication.chart.PlotChart;
import com.example.myapplication.footSteps.FootStepsMore;
import com.example.myapplication.heartRate.HeartRateMore;
import com.example.myapplication.miles.MilesMore;
import com.example.myapplication.readAndSaveAllFile.CalculateData;
import com.example.myapplication.readAndSaveAllFile.ReadAndSaveMultipleFile;
import com.example.myapplication.readAndSaveAllFile.Sleep.SleepFileManager;
import com.example.myapplication.sleep.SleepMore;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import java.util.HashMap;
import java.util.Map;

public class MainScreen extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private View view;
    private Boolean firstCall = true; //avoids duplicate data from storing in 'allData'
    private SwipeRefreshLayout refresh;
    private int Progress = 0;

    //initialize all values of homeScreen
    private TextView valueFootSteps, valueMiles, valueCalories, valueHeartRate, valueHrsSleep,valueMinSleep, valueHrActive, valueMinActive;
    LineChart lineChartHeart;
    PieChart pieChartSleep, pieChartActive;
    CardView cardViewHeart, cardViewSleep, cardViewActive;
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
        valueHrsSleep = view.findViewById(R.id.valueHrsSleepCard);
        valueMinSleep = view.findViewById(R.id.valueMinSleepCard);
        pieChartSleep = view.findViewById(R.id.pieChartSleepHomeScreen);

        view.findViewById(R.id.textSleep).setOnClickListener(this);
        view.findViewById(R.id.textSleepMore).setOnClickListener(this);
        /** //////////////////////////////////////////////////////////////////*/


        /** ///////////////////////////////////////////////////////////////////
         //   initialize Lightly Active cardView attributes                  //
         ///////////////////////////////////////////////////////////////////*/
        cardViewActive = view.findViewById(R.id.ActiveCardView);
        valueHrActive = view.findViewById(R.id.valueHrActiveCard);
        valueMinActive = view.findViewById(R.id.valueMinActiveCard);
        pieChartActive = view.findViewById(R.id.pieChartActiveCard);

        view.findViewById(R.id.textActive).setOnClickListener(this);
        view.findViewById(R.id.textActiveMore).setOnClickListener(this);
        /** //////////////////////////////////////////////////////////////////*/



        // only load new data when user refreshes, so set a flag to track the first call which is when after login
        if (firstCall) {
            getSleepData();
            getAllData();
            firstCall = false;
        }

        //when user sees homeScreen, it check if data is stored or not before setting
        if (ReadAndSaveMultipleFile.hasData ) {
            showTodaysData();
        }
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getAllData() {
        ReadAndSaveMultipleFile readAndSaveMultipleFile = new ReadAndSaveMultipleFile(view.getContext());
        readAndSaveMultipleFile.readAllFilesName();
    }

    private void getSleepData(){
        SleepFileManager sleepFileManager = new SleepFileManager(view.getContext(),true);
    }

    private void updateFootStepsProgress() {
        int totalSteps = (int) CalculateData.getTotal(ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getSteps());
        double totalMiles = CalculateData.getTotal(ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getDistance());
        int totalCalories = (int) CalculateData.getTotal(ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getCalories());


        /** Currently setting the the foot steps goal to 6K
         * progress bar takes value 1-100 so calculating the value to set in progress bar */
        int progressValue = (int) (((double) totalSteps / 6000) * 100);

        ObjectAnimator.ofInt(progressBarFootSteps, "progress", progressValue)
                .setDuration(900)
                .start();

        /**  Currently setting the the miles goal to 2miles */
        progressValue = (int) ((totalMiles / 2) * 100);

        ObjectAnimator.ofInt(progressBarMiles, "progress", progressValue)
                .setDuration(900)
                .start();

        progressValue = (int) (((double) totalCalories / 2000) * 100);
        ObjectAnimator.ofInt(progressBarCalories, "progress", progressValue)
                .setDuration(900)
                .start();
    }

    private void showTodaysData()
    {
        // Don't pull data if it doesn't exist
        if(ReadAndSaveMultipleFile.allData.size() > 0)
        {
            updateFootStepsProgress();

            valueFootSteps.setText(String.valueOf((int) ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getTotalSteps()));

            valueMiles.setText(String.valueOf(ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getTotalDistance()));

            valueCalories.setText(String.valueOf((int) ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getTotalCalories()));

            valueHeartRate.setText(String.valueOf((int) ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getAverageHeartRate()));
        }

        // Don't pull data if it doesn't exist
        if(SleepFileManager.files.size() > 0) {

            valueHrsSleep.setText(String.valueOf(SleepFileManager.files.get(indexOfTodaysData).getTotalHoursSlept()));

            valueMinSleep.setText(String.valueOf(SleepFileManager.files.get(indexOfTodaysData).getTotalMinuteSlept()));

            valueHrActive.setText(String.valueOf(ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getHrActive()));

            valueMinActive.setText(String.valueOf(ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getMinActive()));
        }
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
            if (ReadAndSaveMultipleFile.hasData) {
                PlotChart.lineChart(view.getContext(), true,lineChartHeart, ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getHeartRate(),ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getTimeStamp()); //plots graph using most recent data
            }
            TransitionManager.beginDelayedTransition(cardViewHeart, new AutoTransition());
            lineChartHeart.setVisibility(View.VISIBLE);
        } else {
            lineChartHeart.setVisibility(View.GONE);
        }
    }


    /** takes to screen where user can find more detail information about HeartRate on all dates */
    private void showHeartRateMoreFragment() {
        Fragment fragment = new HeartRateMore();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

    }

    /** this method extends the pie chart for sleep card view */
    private void extendSleepCardView() {
        if (pieChartSleep.getVisibility() == View.GONE) {
            if (ReadAndSaveMultipleFile.hasData) {
                Map<String,Integer> sleepChartData = new HashMap<>(); //store sleep pieChart Data
                sleepChartData.put("WAKE", SleepFileManager.files.get(indexOfTodaysData).getTotalWake());
                sleepChartData.put("LIGHT", SleepFileManager.files.get(indexOfTodaysData).getTotalLight());
                sleepChartData.put("DEEP", SleepFileManager.files.get(indexOfTodaysData).getTotalDeep());
                sleepChartData.put("REM", SleepFileManager.files.get(indexOfTodaysData).getTotalRem());

                PlotChart.pieChart(view.getContext(),true,"sleep",sleepChartData,pieChartSleep);
            }
            TransitionManager.beginDelayedTransition(cardViewSleep, new AutoTransition());
            pieChartSleep.setVisibility(View.VISIBLE);
        } else {
            pieChartSleep.setVisibility(View.GONE);
        }
    }

    /** takes to screen where user can find more detail information about Sleep on all dates */
    private void showSleepMoreFragment() {
        Fragment fragment = new SleepMore();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    /** this method extends the pie chart for Active card view */
    private void extentActiveCard(){
        if(pieChartActive.getVisibility() == View.GONE){
            if(ReadAndSaveMultipleFile.hasData){
                Map<String,Integer> activeChartData = new HashMap<>(); //store active pieChart Data
                activeChartData.put("Sedentary", (int) ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getTotalMinutesSedentary());
                activeChartData.put("Lightly Active", (int) ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getTotalMinutesLightlyActive());
                activeChartData.put("Fairly Active", (int) ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getTotalMinutesFairlyActive());
                activeChartData.put("Very Active", (int) ReadAndSaveMultipleFile.allData.get(indexOfTodaysData).getTotalMinutesVeryActive());

                PlotChart.pieChart(view.getContext(),true, "active",activeChartData, pieChartActive);
            }
            TransitionManager.beginDelayedTransition(cardViewActive, new AutoTransition());
            pieChartActive.setVisibility(View.VISIBLE);
        }
        else {
            pieChartActive.setVisibility(View.GONE);
        }
    }

    /** takes to screen where user can find more detail information about Active on all dates */
    private void showActiveMoreFragment(){
        Fragment fragment = new ActiveMore();
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
            case R.id.textActive:
                extentActiveCard();
                break;
            case R.id.textActiveMore:
                showActiveMoreFragment();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onRefresh() {
        ReadAndSaveMultipleFile.allData.clear();
        getAllData();
        getSleepData();
        if (ReadAndSaveMultipleFile.hasData) { //checking if there is data or not
            showTodaysData();
        }
        refresh.setRefreshing(false);
    }
}

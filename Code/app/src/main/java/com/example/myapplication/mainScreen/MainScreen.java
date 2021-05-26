package com.example.myapplication.mainScreen;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

import com.example.myapplication.LoginStuff.RetrofitClient;
import com.example.myapplication.LoginStuff.User;
import com.example.myapplication.R;
import com.example.myapplication.SharedPrefManager;
import com.example.myapplication.Utility.Score;
import com.example.myapplication.Utility.ScoreAPI;
import com.example.myapplication.active.ActiveMore;
import com.example.myapplication.calories.CaloriesMore;
import com.example.myapplication.chart.PlotChart;
import com.example.myapplication.footSteps.FootStepsMore;
import com.example.myapplication.heartRate.HeartRateMore;
import com.example.myapplication.miles.MilesMore;
import com.example.myapplication.readAndSaveAllFile.Hourly.HourlyAllDataSets;
import com.example.myapplication.readAndSaveAllFile.Hourly.ReadHourlyAllData;
import com.example.myapplication.readAndSaveAllFile.Sleep.SleepFile;
import com.example.myapplication.readAndSaveAllFile.Sleep.SleepFileManager;
import com.example.myapplication.sleep.SleepMore;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainScreen extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "MainScreen";
    private View view;
    private SwipeRefreshLayout refresh;
    private int Progress = 0;
    private int indexOfTodaysData = 0; // since the most recent data is stored in index '0'

    //initialize all values of homeScreen
    private TextView valueScore, valueFootSteps, valueMiles, valueCalories, valueHeartRate, valueHrsSleep, valueMinSleep, valueHrActive, valueMinActive;
    LineChart lineChartHeart;
    PieChart pieChartSleep, pieChartActive;
    CardView cardViewHeart, cardViewSleep, cardViewActive;
    ProgressBar progressBarScore, progressBarFootSteps, progressBarMiles, progressBarCalories;
    ImageButton websiteButton;

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
        /** -----------------------------------------------------------------*/

        /** ///////////////////////////////////////////////////////////////////
         //   initialize health score cardView attributes                    //
         ///////////////////////////////////////////////////////////////////*/
        valueScore = view.findViewById(R.id.valueScoreCard);
        progressBarScore = view.findViewById(R.id.scoreProgress);
        websiteButton = view.findViewById(R.id.websiteButton);
        /** -----------------------------------------------------------------*/

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
        /** -----------------------------------------------------------------*/

        /** ///////////////////////////////////////////////////////////////////
         //   initialize HeartRate cardView attributes                       //
         ///////////////////////////////////////////////////////////////////*/
        cardViewHeart = view.findViewById(R.id.hearRateCardView);
        valueHeartRate = view.findViewById(R.id.valueHeartRateCard);
        lineChartHeart = view.findViewById(R.id.lineChartHeartRateHomeScreen);

        view.findViewById(R.id.textHeartRate).setOnClickListener(this);
        view.findViewById(R.id.textHeartRateMore).setOnClickListener(this);
        /** -----------------------------------------------------------------*/

        /** ///////////////////////////////////////////////////////////////////
         //   initialize Sleep cardView attributes                           //
         ///////////////////////////////////////////////////////////////////*/
        cardViewSleep = view.findViewById(R.id.sleepCardView);
        valueHrsSleep = view.findViewById(R.id.valueHrsSleepCard);
        valueMinSleep = view.findViewById(R.id.valueMinSleepCard);
        pieChartSleep = view.findViewById(R.id.pieChartSleepHomeScreen);

        view.findViewById(R.id.textSleep).setOnClickListener(this);
        view.findViewById(R.id.textSleepMore).setOnClickListener(this);
        /** -----------------------------------------------------------------*/


        /** ///////////////////////////////////////////////////////////////////
         //   initialize Lightly Active cardView attributes                  //
         ///////////////////////////////////////////////////////////////////*/
        cardViewActive = view.findViewById(R.id.ActiveCardView);
        valueHrActive = view.findViewById(R.id.valueHrActiveCard);
        valueMinActive = view.findViewById(R.id.valueMinActiveCard);
        pieChartActive = view.findViewById(R.id.pieChartActiveCard);

        view.findViewById(R.id.textActive).setOnClickListener(this);
        view.findViewById(R.id.textActiveMore).setOnClickListener(this);
        /** -----------------------------------------------------------------*/

        // When the button is clicked, opens the website.
        websiteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.statefarm.com/insurance/life"));
                startActivity(intent);
            }
        });

        new MyTask().execute();

//        // only load new data when user refreshes, so set a flag to track the first call which is when after login
//        if (firstCall) {
//            getSleepData();
//            getAllData();
//            firstCall = false;
//        }

        return view;
    }

    private void updateHealthScoreProgress() {
//        int healthScore = SharedPrefManager.getInstance(getContext()).getScore();

        int healthScore = 4;
        int progressValue = (((6 - healthScore) / 5) * 100);
        ObjectAnimator.ofInt(progressBarScore, "progress", healthScore)
                .setDuration(900)
                .start();
    }

    private void updateFootStepsProgress() {
        int totalSteps = (int) ReadHourlyAllData.HourlyAllData.get(indexOfTodaysData).getTotalSteps();
        double totalMiles = ReadHourlyAllData.HourlyAllData.get(indexOfTodaysData).getTotalDistance();
        int totalCalories = (int) ReadHourlyAllData.HourlyAllData.get(indexOfTodaysData).getTotalCalories();

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

    private void showHealthScore() {
        valueScore.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).getScore()));
    }

    private void showTodaysData() {
        // Don't pull data if it doesn't exist
        if (ReadHourlyAllData.HourlyAllData.size() > 0) {
            updateHealthScoreProgress();

            updateFootStepsProgress();

            valueFootSteps.setText(String.valueOf((int) ReadHourlyAllData.HourlyAllData.get(indexOfTodaysData).getTotalSteps()));

            valueMiles.setText(String.valueOf(ReadHourlyAllData.HourlyAllData.get(indexOfTodaysData).getTotalDistance()));

            valueCalories.setText(String.valueOf((int) ReadHourlyAllData.HourlyAllData.get(indexOfTodaysData).getTotalCalories()));

            valueHeartRate.setText(String.valueOf((int) ReadHourlyAllData.HourlyAllData.get(indexOfTodaysData).getAverageHeartRate()));

            valueHrActive.setText(String.valueOf(ReadHourlyAllData.HourlyAllData.get(indexOfTodaysData).getHrActive()));

            valueMinActive.setText(String.valueOf(ReadHourlyAllData.HourlyAllData.get(indexOfTodaysData).getMinActive()));
        }

        // Don't pull data if it doesn't exist
        if (SleepFileManager.files.size() > 0) {

            valueHrsSleep.setText(String.valueOf(SleepFileManager.files.get(indexOfTodaysData).getTotalHoursSlept()));

            valueMinSleep.setText(String.valueOf(SleepFileManager.files.get(indexOfTodaysData).getTotalMinuteSlept()));
        }
    }

    /**
     * takes to screen where user can find more detail information about FootSteps till date
     */
    private void showFootStepsMoreFragment() {
        Fragment fragment = new FootStepsMore();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //two animation first two for when switching from current to new, last two for returning anim back to current fragment from new
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    /**
     * takes to screen where user can find more detail information about Miles Walked  till date
     */
    private void showMilesMoreFragment() {
        Fragment fragment = new MilesMore();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //two animation first two for when switching from current to new, last two for returning anim back to current fragment from new
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    /**
     * takes to screen where user can find more detail information about calories till date
     */
    private void showCaloriesMoreFragment() {
        Fragment fragment = new CaloriesMore();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }


    /**
     * extends and makes graph visible for heartRate cardView whose initial visibility is in state 'gone'
     */
    private void extendHeartRateCardView() {
        if (lineChartHeart.getVisibility() == View.GONE) {
            if (ReadHourlyAllData.HourlyAllData.size() > 0) {
                PlotChart.lineChart(view.getContext(), true, lineChartHeart, ReadHourlyAllData.HourlyAllData.get(indexOfTodaysData).getHeartRate(), ReadHourlyAllData.HourlyAllData.get(indexOfTodaysData).getTimeStamp()); //plots graph using most recent data
            }
            TransitionManager.beginDelayedTransition(cardViewHeart, new AutoTransition());
            lineChartHeart.setVisibility(View.VISIBLE);
        } else {
            lineChartHeart.setVisibility(View.GONE);
        }
    }


    /**
     * takes to screen where user can find more detail information about HeartRate on all dates
     */
    private void showHeartRateMoreFragment() {
        Fragment fragment = new HeartRateMore();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

    }

    /**
     * this method extends the pie chart for sleep card view
     */
    private void extendSleepCardView() {
        if (pieChartSleep.getVisibility() == View.GONE) {
            if (SleepFileManager.files.size() > 0) {
                Map<String, Integer> sleepChartData = new HashMap<>(); //store sleep pieChart Data
                sleepChartData.put("WAKE", SleepFileManager.files.get(indexOfTodaysData).getTotalWake());
                sleepChartData.put("LIGHT", SleepFileManager.files.get(indexOfTodaysData).getTotalLight());
                sleepChartData.put("DEEP", SleepFileManager.files.get(indexOfTodaysData).getTotalDeep());
                sleepChartData.put("REM", SleepFileManager.files.get(indexOfTodaysData).getTotalRem());

                PlotChart.pieChart(view.getContext(), true, "sleep", sleepChartData, pieChartSleep);
            }
            TransitionManager.beginDelayedTransition(cardViewSleep, new AutoTransition());
            pieChartSleep.setVisibility(View.VISIBLE);
        } else {
            pieChartSleep.setVisibility(View.GONE);
        }
    }

    /**
     * takes to screen where user can find more detail information about Sleep on all dates
     */
    private void showSleepMoreFragment() {
        Fragment fragment = new SleepMore();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    /**
     * this method extends the pie chart for Active card view
     */
    private void extentActiveCard() {
        if (pieChartActive.getVisibility() == View.GONE) {
            if (ReadHourlyAllData.HourlyAllData.size() > 0) {
                Map<String, Integer> activeChartData = new HashMap<>(); //store active pieChart Data
                activeChartData.put("Sedentary", (int) ReadHourlyAllData.HourlyAllData.get(indexOfTodaysData).getTotalMinutesSedentary());
                activeChartData.put("Lightly Active", (int) ReadHourlyAllData.HourlyAllData.get(indexOfTodaysData).getTotalMinutesLightlyActive());
                activeChartData.put("Fairly Active", (int) ReadHourlyAllData.HourlyAllData.get(indexOfTodaysData).getTotalMinutesFairlyActive());
                activeChartData.put("Very Active", (int) ReadHourlyAllData.HourlyAllData.get(indexOfTodaysData).getTotalMinutesVeryActive());

                PlotChart.pieChart(view.getContext(), true, "active", activeChartData, pieChartActive);
            }
            TransitionManager.beginDelayedTransition(cardViewActive, new AutoTransition());
            pieChartActive.setVisibility(View.VISIBLE);
        } else {
            pieChartActive.setVisibility(View.GONE);
        }
    }

    /**
     * takes to screen where user can find more detail information about Active on all dates
     */
    private void showActiveMoreFragment() {
        Fragment fragment = new ActiveMore();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    /**
     * Get user's health score for the server.
     */
    public void getHealthScore() {
        // Set up call objects
        Retrofit retro = new Retrofit.Builder()
                .baseUrl(RetrofitClient.BASE_URL) // Web server url
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ScoreAPI api = retro.create(ScoreAPI.class); // Build API interface
        int id = SharedPrefManager.getInstance(view.getContext()).getUser().getUser_id();
        id = 218817; //testing purpose 'remove this'

        Call<Score> call = api.getUserScore(id); // Build API call

        // Make call
        call.enqueue(new Callback<Score>() {
            @Override
            public void onResponse(Call<Score> call, Response<Score> response) {
                if (response.isSuccessful()) {
                    Log.i("Score", "Health score: " + response.body().getScore());
                    SharedPrefManager.getInstance(view.getContext()).saveScore(response.body().getScore());
                    showHealthScore();
                }
            }

            @Override
            public void onFailure(Call<Score> call, Throwable t) {
                Log.e("Score", "Score call failed");
                SharedPrefManager.getInstance(view.getContext()).saveScore(0);
            }
        });
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
        new MyTask().execute();
    }

    /**
     * background task to the read the data
     */
    class MyTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "AsyncTask Started");

            ReadHourlyAllData.HourlyAllData.clear();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            getHealthScore();

            ReadHourlyAllData readHourlyAllData = new ReadHourlyAllData(view.getContext());
            SleepFileManager sleepFileManager = new SleepFileManager(view.getContext());

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            showTodaysData();
            refresh.setRefreshing(false);
            Log.d(TAG, "Main Screen data load AsyncTask Complete");
        }
    }

    public String getFileName(String fileNameType) {
        String fileName = null;

        //gets today's date in the pattern below
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        date = "2021-04-11";

        //initializing user object from shared preference to get the userID saved during login
        User user = SharedPrefManager.getInstance(view.getContext()).getUser();
        switch (fileName) {
            case "sleep":
                fileName = "Date_" + date + "_User_id_" + /*user.getUser_id()*/"218817" + "_sleepdata.csv";
                break;
            case "summary":
                fileName = "Date_" + date + "_User_id_" + /*user.getUser_id()*/"218817" + "_fitbitdata.csv";
        }
        return fileName;
    }
}

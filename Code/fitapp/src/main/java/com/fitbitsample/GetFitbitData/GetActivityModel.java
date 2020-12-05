package com.fitbitsample.GetFitbitData;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.fitbitsample.FitbitActivity.PrefConstants;
import com.fitbitsample.PaperConstants;
import com.fitbitsample.PaperDB;
import com.fitbitsample.FitbitSharedPref.FitbitPref;
import com.fitbitsample.FitbitSharedPref.FitbitSummary;
import com.fitbitsample.FitbitApiHandling.NetworkListener;
import com.fitbitsample.FitbitApiHandling.RestCall;
import com.fitbitsample.FitbitSharedPref.AppPreference;
import com.fitbitsample.FitbitDataType.ActivityInfo;
import com.fitbitsample.FitbitDataType.Distance;

import java.util.List;
import java.util.Map;


/**
 * This class makes the fitbit API call to get the activities summary of the entire day and running total of the day
 * when the API is called.
 * The application saves the information in both PaperDB and Sharedpreference,
 * Shared preference can let you access the data any where in the application while
 * Paper DB can be utilized within the module.
 */
public class GetActivityModel extends BaseAndroidViewModel<Integer, ActivityInfo, String, GetActivityModel> {
    private Integer activeScore, activityCalories, caloriesBMR, caloriesOut, fairlyActiveMinutes, lightlyActiveMinutes, marginalCalories, sedentaryMinutes, steps, veryActiveMinutes;
    private String distance, total, tracker, loggedActivities, veryActive, moderatelyActive, lightlyActive, sedentaryActive;
    public GetActivityModel(int errorCode) {
        super(true, errorCode);
    }

    @Override
    public GetActivityModel run(final Context context, final String date) {
        restCall = new RestCall<>(context, true);
        restCall.execute(fitbitAPIcalls.getActivitiesByDate(AppPreference.getInstance().getString(PrefConstants.USER_ID), date), new NetworkListener<ActivityInfo>() {
            @Override
            public void success(ActivityInfo activityInfo) {
                if (activityInfo != null) {
                    Log.i("Activity_info:", activityInfo.toString());
                    activeScore = activityInfo.getSummary().getActiveScore();
                    activityCalories = activityInfo.getSummary().getActivityCalories();
                    caloriesBMR = activityInfo.getSummary().getCaloriesBMR();
                    caloriesOut = activityInfo.getSummary().getCaloriesOut();
                    fairlyActiveMinutes = activityInfo.getSummary().getFairlyActiveMinutes();
                    lightlyActiveMinutes = activityInfo.getSummary().getLightlyActiveMinutes();
                    marginalCalories = activityInfo.getSummary().getMarginalCalories();
                    sedentaryMinutes = activityInfo.getSummary().getSedentaryMinutes();
                    steps = activityInfo.getSummary().getSteps();
                    veryActiveMinutes = activityInfo.getSummary().getVeryActiveMinutes();
                    List<Distance> distances = activityInfo.getSummary().getDistances();
                    for(Distance d: distances)
                    {
                        if(d.getActivity().equals("total"))
                        {
                            total = d.getDistance().toString();
                        }
                        else if(d.getActivity().equals("tracker"))
                        {
                            tracker = d.getDistance().toString();
                        }
                        else if(d.getActivity().equals("loggedActivities"))
                        {
                            loggedActivities = d.getDistance().toString();
                        }
                        else if(d.getActivity().equals("veryActive"))
                        {
                            veryActive = d.getDistance().toString();
                        }
                        else if(d.getActivity().equals("moderatelyActive"))
                        {
                            moderatelyActive = d.getDistance().toString();
                        }
                        else if(d.getActivity().equals("lightlyActive"))
                        {
                            lightlyActive = d.getDistance().toString();
                        }
                        else if(d.getActivity().equals("sedentaryActive"))
                        {
                            sedentaryActive = d.getDistance().toString();
                        }
                    }

                    FitbitSummary fitbitSummary = new FitbitSummary(activeScore, activityCalories, caloriesBMR, caloriesOut, fairlyActiveMinutes, lightlyActiveMinutes, marginalCalories, sedentaryMinutes, steps, veryActiveMinutes, total, tracker, loggedActivities, veryActive, moderatelyActive, lightlyActive, sedentaryActive);
                    FitbitPref.getInstance(context).saveFitbitSummary(fitbitSummary);


                    PaperDB.getInstance().write(PaperConstants.ACTIVITY_INFO, activityInfo);
                    data.postValue(0);
                } else {
                    data.postValue(errorCode);
                }
            }

            @Override
            public void headers(Map<String, String> header) {

            }
            @Override
            public void failure() {
                data.postValue(errorCode);
            }
        });
        return this;
    }
}


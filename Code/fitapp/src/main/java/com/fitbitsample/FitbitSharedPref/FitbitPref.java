package com.fitbitsample.FitbitSharedPref;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This preference saves all the desired information retrieved from calling
 * fitbit API in the cache memory of the phone which can be accessed at
 * any point in the application, so the data retrieved from fitbit can
 * be accessed and sent to aws as well.
 */
public class FitbitPref {

    private static final String SHARED_PREF_NAME = "my_shared_pref";

    private static FitbitPref fInstance;
    private Context fCtx;

    private FitbitPref(Context fCtx) {
        this.fCtx = fCtx;
    }
    //save single instance
    public static synchronized FitbitPref getInstance(Context fCtx) {
        if (fInstance == null) //object is not yet created
        {
            fInstance = new FitbitPref(fCtx);
        }
        return fInstance;
    }
    //saves the fitbit user data
    public void savefitbitdata(FitbitUser fitbitUser){

        SharedPreferences sharedPreferences = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("dateOfBirth",fitbitUser.getDateOfBirth());
        editor.putString("fullName",fitbitUser.getFullName());
        editor.putString("gender",fitbitUser.getGender());
        editor.putString("height",fitbitUser.getHeight());
        editor.putString("weight",fitbitUser.getWeight());
        editor.putString("age",fitbitUser.getAge());
        editor.apply();
    }
    //retrieve the fitbit user's data
    public FitbitUser getfitbitUser(){
        SharedPreferences sharedPreferences = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new FitbitUser(
                sharedPreferences.getString("dateOfBirth",null),
                sharedPreferences.getString("fullName",null),
                sharedPreferences.getString("gender",null),
                sharedPreferences.getString("height",null),
                sharedPreferences.getString("weight",null),
                sharedPreferences.getString("age",null)
        );
    }
    //saves the fitbit summary data
    public void saveFitbitSummary(FitbitSummary fitbitSummary) {

        SharedPreferences sharedPreferences = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("activeScore",fitbitSummary.getActiveScore());
        editor.putInt("activityCalories",fitbitSummary.getActivityCalories());
        editor.putInt("caloriesBMR",fitbitSummary.getCaloriesBMR());
        editor.putInt("caloriesOut",fitbitSummary.getCaloriesOut());
        editor.putInt("fairlyActiveMinutes",fitbitSummary.getFairlyActiveMinutes());
        editor.putInt("lightlyActiveMinutes",fitbitSummary.getLightlyActiveMinutes());
        editor.putInt("marginalCalories",fitbitSummary.getMarginalCalories());
        editor.putInt("sedentaryMinutes",fitbitSummary.getSedentaryMinutes());
        editor.putInt("steps",fitbitSummary.getSteps());
        editor.putInt("veryActiveMinutes",fitbitSummary.getVeryActiveMinutes());
        editor.putString("total",fitbitSummary.getTotal());
        editor.putString("tracker",fitbitSummary.getTracker());
        editor.putString("loggedActivities",fitbitSummary.getLoggedActivities());
        editor.putString("veryActive",fitbitSummary.getVeryActive());
        editor.putString("moderatelyActive",fitbitSummary.getModeratelyActive());
        editor.putString("lightlyActive",fitbitSummary.getLightlyActive());
        editor.putString("sedentaryActive",fitbitSummary.getSedentaryActive());
        editor.apply();
    }
    //retrieve the fitbit summary data
    public FitbitSummary getfitbitSummary() {
        SharedPreferences sharedPreferences = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new FitbitSummary(
                sharedPreferences.getInt("activeScore",0),
                sharedPreferences.getInt("activityCalories",0),
                sharedPreferences.getInt("caloriesBMR",0),
                sharedPreferences.getInt("caloriesOut",0),
                sharedPreferences.getInt("fairlyActiveMinutes",0),
                sharedPreferences.getInt("lightlyActiveMinutes",0),
                sharedPreferences.getInt("marginalCalories",0),
                sharedPreferences.getInt("sedentaryMinutes",0),
                sharedPreferences.getInt("steps",0),
                sharedPreferences.getInt("veryActiveMinutes",0),
                sharedPreferences.getString("total",null),
                sharedPreferences.getString("tracker",null),
                sharedPreferences.getString("loggedActivities",null),
                sharedPreferences.getString("veryActive",null),
                sharedPreferences.getString("moderatelyActive",null),
                sharedPreferences.getString("lightlyActive",null),
                sharedPreferences.getString("sedentaryActive",null)
        );
    }
    //saves all the heartdata in one giant string
    public void saveHeartData(HeartRateInfo heartRateInfo) {

        SharedPreferences sharedPreferences = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("heartdata",heartRateInfo.getHeartdata());
        editor.apply();
    }
    //retrieve the heart data
    public HeartRateInfo getHeartdata() {
        SharedPreferences sharedPreferences = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new HeartRateInfo(
                sharedPreferences.getString("heartdata",null)
        );
    }


}

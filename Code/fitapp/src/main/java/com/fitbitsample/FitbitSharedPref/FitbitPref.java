package com.fitbitsample.FitbitSharedPref;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fitbitsample.FitbitDataType.HeartRate;
import com.fitbitsample.FitbitDataType.HeartRateZone;
import com.fitbitsample.FitbitDataType.Hourly.Dataset;
import com.fitbitsample.FitbitDataType.Hourly.HourlyCalorie;
import com.fitbitsample.FitbitDataType.Hourly.HourlyDistance;
import com.fitbitsample.FitbitDataType.Hourly.HourlyElevation;
import com.fitbitsample.FitbitDataType.Hourly.HourlyFloor;
import com.fitbitsample.FitbitDataType.Hourly.HourlyStep;
import com.fitbitsample.FitbitDataType.SleepData.Data;
import com.fitbitsample.FitbitDataType.SleepData.Sleep;

import java.util.ArrayList;
import java.util.List;

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


    /**
     * Saves the fitbit summary data
     * @param fitbitSummary
     */
    public void saveFitbitSummary(FitbitSummary fitbitSummary)
    {
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


    /**
     * Retrieve the fitbit summary data
     * @return
     */
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



    /**
     * Save the intraday elevation data.
     * @param e Elevation data
     */
    public void saveElevationData(HourlyElevation e)
    {
        if(e.getActivitiesElevationIntraday().getDataset().size() < 1)
        {
            setElevationZeros();
            return;
        }

        SharedPreferences sharedPreferences = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String base = "elevation";
        int size = e.getActivitiesElevationIntraday().getDataset().size(); // Size of data set
        for(int i = 0; i < size; i++) // Set response data
        {
            // Base + i is calorie column. i.e. elevation0, elevation1, ..., elevation96
            editor.putFloat(base + "Value" + i, e.getActivitiesElevationIntraday().getDataset().get(i).getValue());
            editor.putString(base + "Time" + i, e.getActivitiesElevationIntraday().getDataset().get(i).getTime());
        }


        // Fill rest with blank data
        // 96 is from 15 min increments * 24 hours
        for(int i = size; i < 96; i++)
        {
            editor.putFloat(base + "Value" + i, 0.0f);
            editor.putString(base + "Time" + i, "N/A");
        }

        editor.apply();
    }


    /**
     * Sets the values to zero or default strings.
     */
    private void setElevationZeros()
    {
        SharedPreferences sharedPreferences = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String base = "elevation";
        for(int i = 0; i < 96; i++)
        {
            editor.putFloat(base + "Value" + i, 0.0f);
            editor.putString(base + "Time" + i, "N/A");
        }

        editor.apply();
    }


    /**
     * Get the intraday elevation data.
     * @return ArrayList of Time, Value data
     */
    public ArrayList<Dataset> getElevationData()
    {
        ArrayList<Dataset> list = new ArrayList(96);
        String base = "elevation"; // Base name
        SharedPreferences pref = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        for(int i = 0; i < 96; i++)
        {
            Dataset d = new Dataset();
            d.setValue(pref.getFloat(base + "Value" + i, 0.0f)); // Defaults to zero
            d.setTime(pref.getString(base + "Time" + i, "N/A"));

            list.add(d);
        }

        return list;
    }


    /**
     * Save the intraday floors data.
     * @param f Floors data
     */
    public void saveFloorsData(HourlyFloor f)
    {
        if(f.getActivitiesFloorsIntraday().getDataset().size() < 1)
        {
            setFloorsZeros();
            return;
        }

        SharedPreferences sharedPreferences = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String base = "floors";
        int size = f.getActivitiesFloorsIntraday().getDataset().size(); // Size of data set
        for(int i = 0; i < size; i++) // Set response data
        {
            // Base + i is calorie column. i.e. floors0, floors1, ..., floors96
            editor.putFloat(base + "Value" + i, f.getActivitiesFloorsIntraday().getDataset().get(i).getValue());
            editor.putString(base + "Time" + i, f.getActivitiesFloorsIntraday().getDataset().get(i).getTime());
        }


        // Fill rest with blank data
        // 96 is from 15 min increments * 24 hours
        for(int i = size; i < 96; i++)
        {
            editor.putFloat(base + "Value" + i, 0.0f);
            editor.putString(base + "Time" + i, "N/A");
        }

        editor.apply();
    }


    /**
     * Sets the values to zero or default strings.
     */
    private void setFloorsZeros()
    {
        SharedPreferences sharedPreferences = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String base = "floors";
        for(int i = 0; i < 96; i++)
        {
            editor.putFloat(base + "Value" + i, 0.0f);
            editor.putString(base + "Time" + i, "N/A");
        }

        editor.apply();
    }


    /**
     * Get the intraday floors data.
     * @return ArrayList of Time, Value data
     */
    public ArrayList<Dataset> getFloorsData()
    {
        ArrayList<Dataset> list = new ArrayList(96);
        String base = "floors"; // Base name
        SharedPreferences pref = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        for(int i = 0; i < 96; i++)
        {
            Dataset d = new Dataset();
            d.setValue(pref.getFloat(base + "Value" + i, 0.0f)); // Defaults to zero
            d.setTime(pref.getString(base + "Time" + i, "N/A"));

            list.add(d);
        }

        return list;
    }


    /**
     * Save the intraday distance data from FitBit.
     * @param d Distance data
     */
    public void saveDistanceData(HourlyDistance d)
    {
        if(d.getActivitiesDistanceIntraday().getDataset().size() < 1)
        {
            setDistanceZeros();
            return;
        }

        SharedPreferences sharedPreferences = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String base = "distance";
        int size = d.getActivitiesDistanceIntraday().getDataset().size(); // Size of data set
        for(int i = 0; i < size; i++) // Set response data
        {
            // Base + i is calorie column. i.e. distance0, distance1, ..., distance96
            editor.putFloat(base + "Value" + i, d.getActivitiesDistanceIntraday().getDataset().get(i).getValue());
            editor.putString(base + "Time" + i, d.getActivitiesDistanceIntraday().getDataset().get(i).getTime());
        }


        // Fill rest with blank data
        // 96 is from 15 min increments * 24 hours
        for(int i = size; i < 96; i++)
        {
            editor.putFloat(base + "Value" + i, 0.0f);
            editor.putString(base + "Time" + i, "N/A");
        }

        editor.apply();
    }


    /**
     * Sets the values to zero or default strings.
     */
    private void setDistanceZeros()
    {
        SharedPreferences sharedPreferences = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String base = "distance";
        for(int i = 0; i < 96; i++) // Set response data
        {
            editor.putFloat(base + "Value" + i, 0.0f);
            editor.putString(base + "Time" + i, "N/A");
        }

        editor.apply();
    }


    /**
     * Gets the intraday distance data.
     * @return ArrayList of Time, Value data
     */
    public ArrayList<Dataset> getDistanceData()
    {
        ArrayList<Dataset> list = new ArrayList(96);
        String base = "distance"; // Base name
        SharedPreferences pref = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        for(int i = 0; i < 96; i++)
        {
            Dataset d = new Dataset();
            d.setValue(pref.getFloat(base + "Value" + i, 0.0f)); // Defaults to zero
            d.setTime(pref.getString(base + "Time" + i, "N/A"));

            list.add(d);
        }

        return list;
    }


    /**
     * Save the heart rate information including intraday data
     * if it exists.
     */
    public void saveHeartData(HeartRate info)
    {
        if(info.getActivitiesHeart().size() < 1 || info.getActivitiesHeartIntraday().getDataset().size() < 1)
        {
            setHeartZeros();
            return;
        }

        SharedPreferences sharedPreferences = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("restingHeartRate", info.getActivitiesHeart().get(0).getValue().getRestingHeartRate());

        List<HeartRateZone> zone = info.getActivitiesHeart().get(0).getValue().getHeartRateZones(); // All heart zones
        editor.putFloat("rangeCalorie", zone.get(0).getCaloriesOut());
        editor.putInt("rangeMin", zone.get(0).getMin());
        editor.putInt("rangeMax", zone.get(0).getMax());
        editor.putInt("rangeMinute", zone.get(0).getMinutes());

        editor.putFloat("fatCalorie", zone.get(1).getCaloriesOut());
        editor.putInt("fatMin", zone.get(1).getMin());
        editor.putInt("fatMax", zone.get(1).getMax());
        editor.putInt("fatMinute", zone.get(1).getMinutes());

        editor.putFloat("cardioCalorie", zone.get(2).getCaloriesOut());
        editor.putInt("cardioMin", zone.get(2).getMin());
        editor.putInt("cardioMax", zone.get(2).getMax());
        editor.putInt("cardioMinute", zone.get(2).getMinutes());

        editor.putFloat("peakCalorie", zone.get(3).getCaloriesOut());
        editor.putInt("peakMin", zone.get(3).getMin());
        editor.putInt("peakMax", zone.get(3).getMax());
        editor.putInt("peakMinute", zone.get(3).getMinutes());

        // Save intraday if it exists
        if(info.getActivitiesHeartIntraday().getDataset().size() > 0)
        {
            String base = "heart";
            int size = info.getActivitiesHeartIntraday().getDataset().size();
            List<Dataset> set = info.getActivitiesHeartIntraday().getDataset();
            editor.putInt(base + "Size", size);

            // Set vals
            // Keys: heartTime0, heartTime1, ..., heartTimeSize
            for(int i = 0; i < size; i++)
            {
                editor.putString(base + "Time" + i, set.get(i).getTime());
                editor.putFloat(base + "Value" + i, set.get(i).getValue());
            }

            // Set rest of list to 0
            for(int i = size; i < 96; i++)
            {
                editor.putString(base + "Time" + i, "N/A");
                editor.putFloat(base + "Value" + i, 0.0f);
            }
        }

        editor.apply();
    }


    /**
     * Sets the values to zeros and default strings.
     */
    private void setHeartZeros()
    {
        SharedPreferences sharedPreferences = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putInt("restingHeartRate", 0);

        editor.putFloat("rangeCalorie", 0.0f);
        editor.putInt("rangeMin", 0);
        editor.putInt("rangeMax", 0);
        editor.putInt("rangeMinute", 0);

        editor.putFloat("fatCalorie", 0.0f);
        editor.putInt("fatMin", 0);
        editor.putInt("fatMax", 0);
        editor.putInt("fatMinute", 0);

        editor.putFloat("cardioCalorie", 0.0f);
        editor.putInt("cardioMin", 0);
        editor.putInt("cardioMax", 0);
        editor.putInt("cardioMinute", 0);

        editor.putFloat("peakCalorie", 0.0f);
        editor.putInt("peakMin", 0);
        editor.putInt("peakMax", 0);
        editor.putInt("peakMinute", 0);

        String base = "heart";
        editor.putInt(base + "Size", 0);

        for(int i = 0; i < 96; i++)
        {
            editor.putString(base + "Time" + i, "N/A");
            editor.putFloat(base + "Value" + i, 0.0f);
        }

        editor.apply();
    }


    /**
     * retrieve the heart data
     */
    public HeartRateInfo getHeartdata() {
        SharedPreferences pref = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        HeartRateInfo info  = new HeartRateInfo(pref.getInt("restingHeartRate", 0));

        info.setRange(pref.getFloat("rangeCalorie", 0), pref.getInt("rangeMin", 0), pref.getInt("rangeMax", 0), pref.getInt("rangeMinute", 0));
        info.setFat(pref.getFloat("fatCalorie", 0), pref.getInt("fatMin", 0), pref.getInt("fatMax", 0), pref.getInt("fatMinute", 0));
        info.setCardio(pref.getFloat("cardioCalorie", 0), pref.getInt("cardioMin", 0), pref.getInt("cardioMax", 0), pref.getInt("cardioMinute", 0));
        info.setPeak(pref.getFloat("peakCalorie", 0), pref.getInt("peakMin", 0), pref.getInt("peakMax", 0), pref.getInt("peakMinute", 0));


        String base = "heart";
        int size = pref.getInt(base + "Size", 0);

        for(int i = 0; i < size; i++)
        {
            Dataset set = new Dataset();
            set.setTime(pref.getString(base + "Time" + i, "N/A"));
            set.setValue(pref.getFloat(base + "Value" + i, 0.0f));
            info.addSet(set);
        }

        for(int i = size; i < 96; i++)
        {
            Dataset set = new Dataset();
            set.setTime("N/A");
            set.setValue(0.0f);
            info.addSet(set);
        }

        return info;
    }


    /**
     * Save the intraday step activity.
     * @param step Intraday steps to save
     */
    public void setStepData(HourlyStep step)
    {
        if(step.getActivitiesStepsIntraday().getDataset().size() < 1)
        {
            setStepZeros();
            return;
        }

        // Get SharedPreferences instance and set to edit
        SharedPreferences pref = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();


        String base = "steps";
        int size = step.getActivitiesStepsIntraday().getDataset().size(); // Size of data set
        for(int i = 0; i < size; i++) // Set response data
        {
            // Base + i is calorie column. i.e. steps0, steps1, ..., steps96
            editor.putFloat(base + "Value" + i, step.getActivitiesStepsIntraday().getDataset().get(i).getValue());
            editor.putString(base + "Time" + i, step.getActivitiesStepsIntraday().getDataset().get(i).getTime());
        }


        // Fill rest with blank data
        // 96 is from 15 min increments * 24 hours
        for(int i = size; i < 96; i++)
        {
            editor.putFloat(base + "Value" + i, 0.0f);
        }

        editor.apply();
    }


    /**
     * Sets steps values to zero or default strings.
     */
    private void setStepZeros()
    {
        SharedPreferences pref = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String base = "steps";
        for(int i = 0; i < 96; i++) // Set response data
        {
            // Base + i is calorie column. i.e. steps0, steps1, ..., steps96
            editor.putFloat(base + "Value" + i, 0.0f);
            editor.putString(base + "Time" + i, "N/A");
        }
    }


    /**
     * Returns a list of 96 elements for the step data.<br />
     * Data: Time and Value
     * @return list of intraday data
     */
    public ArrayList<Dataset> getStepData()
    {
        ArrayList<Dataset> list = new ArrayList(96);
        String base = "steps"; // Base name
        SharedPreferences pref = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        for(int i = 0; i < 96; i++)
        {
            Dataset d = new Dataset();
            d.setValue(pref.getFloat(base + "Value" + i, 0.0f)); // Defaults to zero
            d.setTime(pref.getString(base + "Time" + i, "N/A"));

            list.add(d);
        }

        return list;
    }



    /**
     * Save the sleep data from FitBit call to SharedPreferences.
     * @param sleep Fitbit return data
     */
    public void setSleepData(Sleep sleep)
    {
        if(sleep.getSleep().size() < 1) // Set sleep states to zeros
        {
            setSleepZeros();
            return;
        }


        // Get SharedPreferences instance and set to edit
        SharedPreferences pref = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();


        // Meta data
        editor.putString("dateOfSleep", sleep.getSleep().get(0).getDateOfSleep());
        editor.putInt("duration", sleep.getSleep().get(0).getDuration());
        editor.putInt("efficiency", sleep.getSleep().get(0).getEfficiency());
        editor.putInt("minutesAsleep", sleep.getSleep().get(0).getMinutesAsleep());
        editor.putInt("minutesAwake", sleep.getSleep().get(0).getMinutesAwake());
        editor.putInt("minutesToFallAsleep", sleep.getSleep().get(0).getMinutesToFallAsleep());
        editor.putString("startTime", sleep.getSleep().get(0).getStartTime().substring(11)); // 2017-04-01T23:58:30.000

        // Summary data
        editor.putInt("deepCount", sleep.getSleep().get(0).getLevels().getSummary().getDeep().getCount());
        editor.putInt("deepAvg", sleep.getSleep().get(0).getLevels().getSummary().getDeep().getThirtyDayAvgMinutes());
        editor.putInt("deepMinutes", sleep.getSleep().get(0).getLevels().getSummary().getDeep().getMinutes());

        editor.putInt("lightCount", sleep.getSleep().get(0).getLevels().getSummary().getLight().getCount());
        editor.putInt("lightAvg", sleep.getSleep().get(0).getLevels().getSummary().getLight().getThirtyDayAvgMinutes());
        editor.putInt("lightMinutes", sleep.getSleep().get(0).getLevels().getSummary().getLight().getMinutes());

        editor.putInt("remCount", sleep.getSleep().get(0).getLevels().getSummary().getRem().getCount());
        editor.putInt("remAvg", sleep.getSleep().get(0).getLevels().getSummary().getRem().getThirtyDayAvgMinutes());
        editor.putInt("remMinutes", sleep.getSleep().get(0).getLevels().getSummary().getRem().getMinutes());

        editor.putInt("wakeCount", sleep.getSleep().get(0).getLevels().getSummary().getWake().getCount());
        editor.putInt("wakeAvg", sleep.getSleep().get(0).getLevels().getSummary().getWake().getThirtyDayAvgMinutes());
        editor.putInt("wakeMinutes", sleep.getSleep().get(0).getLevels().getSummary().getWake().getMinutes());

        // List of state changes
        String base = "sleepState";
        List<Data> data = sleep.getSleep().get(0).getLevels().getData(); // Get state data
        int max = data.size(); // Size of list
        editor.putInt("dataSize", max);

        for(int i = 0; i < max; i++)
        {
            editor.putString(base + "Level" + i, data.get(i).getLevel());
            editor.putInt(base + "Seconds" + i, data.get(i).getSeconds());
            editor.putString(base + "Time" + i, data.get(i).getDateTime().substring(11)); // 2017-04-02T00:16:30.000
        }

        editor.apply(); // Save changes
    }


    /**
     * Sets the sleep data to zeros or default strings.
     */
    private void setSleepZeros()
    {
        SharedPreferences pref = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();


        // Meta data
        editor.putString("dateOfSleep", "N/A");
        editor.putInt("duration", 0);
        editor.putInt("efficiency", 0);
        editor.putInt("minutesAsleep", 0);
        editor.putInt("minutesAwake", 0);
        editor.putInt("minutesToFallAsleep", 0);
        editor.putString("startTime", "N/A"); // 2017-04-01T23:58:30.000

        // Summary data
        editor.putInt("deepCount", 0);
        editor.putInt("deepAvg", 0);
        editor.putInt("deepMinutes", 0);

        editor.putInt("lightCount", 0);
        editor.putInt("lightAvg", 0);
        editor.putInt("lightMinutes", 0);

        editor.putInt("remCount", 0);
        editor.putInt("remAvg", 0);
        editor.putInt("remMinutes", 0);

        editor.putInt("wakeCount", 0);
        editor.putInt("wakeAvg", 0);
        editor.putInt("wakeMinutes", 0);


        // List of state changes
        String base = "sleepState";
        int max = pref.getInt("dataSize", 0); // Get last saved value or 0
        editor.putInt("dataSize", 0);

        for(int i = 0; i < max; i++)
        {
            editor.putString(base + "Level" + i, "N/A");
            editor.putInt(base + "Seconds" + i, 0);
            editor.putString(base + "Time" + i, "N/A"); // 2017-04-02T00:16:30.000
        }


        editor.apply(); // Save changes
    }


    /**
     * Retuns the sleep data stored in SharedPreferences.
     * @return Sleep object
     */
    public SleepInfo getSleepData()
    {
        SharedPreferences pref = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SleepInfo s = new SleepInfo();

        s.setDate(pref.getString("dateOfSleep", "N/A"));
        s.setDuration(pref.getInt("duration", 0));
        s.setEfficiency(pref.getInt("efficiency", 0));
        s.setMinutesAsleep(pref.getInt("minutesAsleep", 0));
        s.setMinutesAwake(pref.getInt("minutesAwake", 0));
        s.setMinuteToFallAsleep(pref.getInt("minutesToFallAsleep", 0));
        s.setTime(pref.getString("startTime", "N/A"));

        s.setDeepAvg(pref.getInt("deepAvg", 0));
        s.setDeepCount(pref.getInt("deepCount", 0));
        s.setDeepMinutes(pref.getInt("deepMinutes", 0));

        s.setLightAvg(pref.getInt("lightAvg", 0));
        s.setLightCount(pref.getInt("lightCount", 0));
        s.setLightMinutes(pref.getInt("lightMinutes", 0));

        s.setRemAvg(pref.getInt("remAvg", 0));
        s.setRemCount(pref.getInt("remCount", 0));
        s.setRemMinutes(pref.getInt("remMinutes", 0));

        s.setWakeAvg(pref.getInt("wakeAvg", 0));
        s.setWakeCount(pref.getInt("wakeCount", 0));
        s.setWakeMinutes(pref.getInt("wakeMinutes", 0));


        String base = "sleepState";
        int max = pref.getInt("dataSize", 0);

        // Vars to fill
        String level, time;
        int sec;
        for(int i = 0; i < max; i++)
        {
            level = pref.getString(base + "Level" + i, "N/A");
            time = pref.getString(base + "Time" + i, "N/A");
            sec = pref.getInt(base + "Seconds" + i, 0);

            s.addData(level, sec, time);
        }

        return s;
    }


    /**
     * Saves the time/value pairs for all 15 minute increments of calories burned.
     * @param cal Filled API response
     */
    public void setCalorieData(HourlyCalorie cal)
    {
        if(cal.getActivitiesCaloriesIntraday().getDataset().size() < 1)
        {
            setCalorieZeros();
            return;
        }

        SharedPreferences sharedPreferences = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String base = "calories";
        int size = cal.getActivitiesCaloriesIntraday().getDataset().size(); // Size of data set
        for(int i = 0; i < size; i++) // Set response data
        {
            // Base + i is calorie column. i.e. calories0, calories1, ..., calories96
            editor.putFloat(base + "Value" + i, cal.getActivitiesCaloriesIntraday().getDataset().get(i).getValue());
            editor.putInt(base + "Level" + i, cal.getActivitiesCaloriesIntraday().getDataset().get(i).getLevel());
            editor.putString(base + "Time" + i, cal.getActivitiesCaloriesIntraday().getDataset().get(i).getTime());
            editor.putInt(base + "Mets" + i, cal.getActivitiesCaloriesIntraday().getDataset().get(i).getMets());
        }


        // Fill rest with blank data
        // 96 is from 15 min increments * 24 hours
        for(int i = size; i < 96; i++)
        {
            editor.putFloat(base + "Value" + i, 0.0f);
            editor.putInt(base + "Level" + i, 0);
            editor.putInt(base + "Mets" + i, 0);
        }

        editor.apply();
    }


    /**
     * Set the calorie data to zeros and default strings.
     */
    private void setCalorieZeros()
    {
        SharedPreferences sharedPreferences = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String base = "calories";
        for(int i = 0; i < 96; i++) // Set response data
        {
            // Base + i is calorie column. i.e. calories0, calories1, ..., calories96
            editor.putFloat(base + "Value" + i, 0.0f);
            editor.putInt(base + "Level" + i, 0);
            editor.putString(base + "Time" + i, "N/A");
            editor.putInt(base + "Mets" + i, 0);
        }

        editor.apply();
    }


    /**
     * Returns a list of 96 elements for the calorie data.<br />
     * Data: Time, Level, Mets, and Value
     * @return list of intraday data
     */
    public ArrayList<Dataset> getCalorieData()
    {
        ArrayList<Dataset> list = new ArrayList(96);
        String base = "calories"; // Base name
        SharedPreferences pref = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        for(int i = 0; i < 96; i++)
        {
            Dataset d = new Dataset();
            d.setValue(pref.getFloat(base + "Value" + i, 0.0f)); // Defaults to zero
            d.setLevel(pref.getInt(base + "Level" + i, 0));
            d.setMets(pref.getInt(base + "Mets" + i, 0));
            d.setTime(pref.getString(base + "Time" + i, "N/A"));

            list.add(d);
        }

        return list;
    }
}

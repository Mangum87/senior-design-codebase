package com.fitbitsample.FitbitSharedPref;

import android.content.Context;
import android.content.SharedPreferences;

import com.fitbitsample.FitbitDataType.Device;
import com.fitbitsample.FitbitDataType.Hourly.HourlyCalorie;
import com.fitbitsample.FitbitDataType.SleepData.Data;
import com.fitbitsample.FitbitDataType.SleepData.Sleep;

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
    public void saveHeartData(HeartRateInfo info) {

        SharedPreferences sharedPreferences = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("restingHeartRate", info.getRestingRate());

        editor.putInt("rangeCalorie", info.getRangeCalorie());
        editor.putInt("rangeMin", info.getRangeMin());
        editor.putInt("rangeMax", info.getRangeMax());
        editor.putInt("rangeMinute", info.getRangeMinutes());

        editor.putInt("fatCalorie", info.getFatCalorie());
        editor.putInt("fatMin", info.getFatMin());
        editor.putInt("fatMax", info.getFatMax());
        editor.putInt("fatMinute", info.getFatMinutes());

        editor.putInt("cardioCalorie", info.getCardioCalorie());
        editor.putInt("cardioMin", info.getCardioMin());
        editor.putInt("cardioMax", info.getCardioMax());
        editor.putInt("cardioMinute", info.getCardioMinutes());

        editor.putInt("peakCalorie", info.getPeakCalorie());
        editor.putInt("peakMin", info.getPeakMin());
        editor.putInt("peakMax", info.getPeakMax());
        editor.putInt("peakMinute", info.getPeakMinutes());

        editor.apply();
    }
    //retrieve the heart data
    public HeartRateInfo getHeartdata() {
        SharedPreferences pref = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        HeartRateInfo info  = new HeartRateInfo(pref.getInt("restingHeartRate", 0));

        info.setRange(pref.getInt("rangeCalorie", 0), pref.getInt("rangeMin", 0), pref.getInt("rangeMax", 0), pref.getInt("rangeMinute", 0));
        info.setFat(pref.getInt("fatCalorie", 0), pref.getInt("fatMin", 0), pref.getInt("fatMax", 0), pref.getInt("fatMinute", 0));
        info.setCardio(pref.getInt("cardioCalorie", 0), pref.getInt("cardioMin", 0), pref.getInt("cardioMax", 0), pref.getInt("cardioMinute", 0));
        info.setPeak(pref.getInt("peakCalorie", 0), pref.getInt("peakMin", 0), pref.getInt("peakMax", 0), pref.getInt("peakMinute", 0));

        return info;
    }


    /**
     * Save the sleep data from FitBit call to SharedPreferences.
     * @param sleep Fitbit return data
     */
    public void setSleepData(Sleep sleep)
    {
        if(sleep.getSleep().size() < 1)
            return;

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
        SharedPreferences sharedPreferences = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String base = "calories";
        int size = cal.getActivitiesCaloriesIntraday().getDataset().size(); // Size of data set
        for(int i = 0; i < size; i++) // Set response data
        {
            // Base + i is calorie column. i.e. calories0, calories1, ..., calories96
            editor.putInt(base + i, cal.getActivitiesCaloriesIntraday().getDataset().get(i - 1).getValue());
        }


        // Fill rest with blank data
        // 96 is from 15 min increments * 24 hours
        for(int i = size; i < 96; i++)
        {
            editor.putInt(base + i, 0);
        }

        editor.apply();
    }


    /**
     * Returns an array of 96 int elements for the calorie data.
     * @return int array
     */
    public int[] getCalorieData()
    {
        int[] vals = new int[96];
        String base = "calories"; // Base name
        SharedPreferences pref = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        for(int i = 0; i < 96; i++)
        {
            vals[i] = pref.getInt(base + i, 0); // Defaults to zero
        }

        return vals;
    }


    /**
     * Save single device data to SharedPreferences.
     * @param device
     */
    public void setDeviceData(Device device)
    {
        SharedPreferences sharedPreferences = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("battery", device.getBattery());
        editor.putString("deviceVersion", device.getVersion());
        editor.putString("lastSyncTime", device.getLastSync());
        editor.putString("id", device.getID());
        editor.putString("type", device.getType());

        editor.apply(); // Save changes made
    }


    /**
     * Returns a single device of the user.
     * @return Single device
     */
    public Device getDevice()
    {
        SharedPreferences pref = fCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Device d = new Device();

        d.setBattery(pref.getString("battery", null));
        d.setVersion(pref.getString("deviceVersion", null));
        d.setID(pref.getString("id", null));
        d.setLastSync(pref.getString("lastSyncTime", null));
        d.setType(pref.getString("type", null));


        return d;
    }
}

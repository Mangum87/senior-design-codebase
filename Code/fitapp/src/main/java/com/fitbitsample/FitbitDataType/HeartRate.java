package com.fitbitsample.FitbitDataType;

import com.fitbitsample.FitbitDataType.Hourly.Intraday;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/*
    Creating a viewing adapter class for parsing gson file of heartrate received from Fitbit API call
 */

import java.util.List;

public class HeartRate
{
    @SerializedName("activities-heart")
    @Expose
    private List<ActivitiesHeart> activitiesHeart = null;

    @SerializedName("activities-heart-intraday")
    @Expose
    private Intraday activitiesHeartIntraday;

    public List<ActivitiesHeart> getActivitiesHeart() {
        return activitiesHeart;
    }

    public void setActivitiesHeart(List<ActivitiesHeart> activitiesHeart) {
        this.activitiesHeart = activitiesHeart;
    }

    public Intraday getActivitiesHeartIntraday() {
        return activitiesHeartIntraday;
    }

    public void setActivitiesHeartIntraday(Intraday activitiesHeartIntraday) {
        this.activitiesHeartIntraday = activitiesHeartIntraday;
    }


    @Override
    public String toString() {
        return "HeartRate{" +
                "activitiesHeart=" + activitiesHeart +
                '}';
    }
}

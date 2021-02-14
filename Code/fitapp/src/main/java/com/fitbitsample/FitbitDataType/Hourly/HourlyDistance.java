package com.fitbitsample.FitbitDataType.Hourly;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HourlyDistance
{
    @SerializedName("activities-distance")
    @Expose
    private List<HourlySummary> activitiesDistance = null;

    @SerializedName("activities-distance-intraday")
    @Expose
    private Intraday activitiesDistanceIntraday;

    public List<HourlySummary> getActivitiesDistance() {
        return activitiesDistance;
    }

    public void setActivitiesDistance(List<HourlySummary> activitiesDistance) {
        this.activitiesDistance = activitiesDistance;
    }

    public Intraday getActivitiesDistanceIntraday() {
        return activitiesDistanceIntraday;
    }

    public void setActivitiesDistanceIntraday(Intraday activitiesDistanceIntraday) {
        this.activitiesDistanceIntraday = activitiesDistanceIntraday;
    }
}

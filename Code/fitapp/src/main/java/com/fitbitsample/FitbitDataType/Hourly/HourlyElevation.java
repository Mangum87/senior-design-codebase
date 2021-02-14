package com.fitbitsample.FitbitDataType.Hourly;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HourlyElevation
{
    @SerializedName("activities-elevation")
    @Expose
    private List<HourlySummary> activitiesElevation = null;

    @SerializedName("activities-elevation-intraday")
    @Expose
    private Intraday activitiesElevationIntraday;

    public List<HourlySummary> getActivitiesElevation() {
        return activitiesElevation;
    }

    public void setActivitiesElevation(List<HourlySummary> activitiesElevation) {
        this.activitiesElevation = activitiesElevation;
    }

    public Intraday getActivitiesElevationIntraday() {
        return activitiesElevationIntraday;
    }

    public void setActivitiesElevationIntraday(Intraday activitiesElevationIntraday) {
        this.activitiesElevationIntraday = activitiesElevationIntraday;
    }
}

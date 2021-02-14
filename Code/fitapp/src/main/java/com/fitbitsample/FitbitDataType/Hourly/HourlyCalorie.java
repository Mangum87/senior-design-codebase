package com.fitbitsample.FitbitDataType.Hourly;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HourlyCalorie
{
    @SerializedName("activities-calories")
    @Expose
    private List<HourlySummary> activitiesCalories = null;

    @SerializedName("activities-calories-intraday")
    @Expose
    private Intraday activitiesCaloriesIntraday;

    public List<HourlySummary> getActivitiesCalories() {
        return activitiesCalories;
    }

    public void setActivitiesCalories(List<HourlySummary> activitiesCalories) {
        this.activitiesCalories = activitiesCalories;
    }

    public Intraday getActivitiesCaloriesIntraday() {
        return activitiesCaloriesIntraday;
    }

    public void setActivitiesCaloriesIntraday(Intraday activitiesCaloriesIntraday) {
        this.activitiesCaloriesIntraday = activitiesCaloriesIntraday;
    }
}

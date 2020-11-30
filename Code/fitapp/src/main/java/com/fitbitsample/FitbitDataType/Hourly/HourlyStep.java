package com.fitbitsample.FitbitDataType.Hourly;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HourlyStep
{
    @SerializedName("activities-steps")
    @Expose
    private List<HourlySummary> activitiesSteps = null;

    @SerializedName("activities-steps-intraday")
    @Expose
    private Intraday activitiesStepsIntraday;

    public List<HourlySummary> getActivitiesSteps() {
        return activitiesSteps;
    }

    public void setActivitiesSteps(List<HourlySummary> activitiesSteps) {
        this.activitiesSteps = activitiesSteps;
    }

    public Intraday getActivitiesStepsIntraday() {
        return activitiesStepsIntraday;
    }

    public void setActivitiesStepsIntraday(Intraday activitiesStepsIntraday) {
        this.activitiesStepsIntraday = activitiesStepsIntraday;
    }
}

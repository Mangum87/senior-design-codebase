package com.fitbitsample.FitbitDataType;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Creating a viewing adapter class for parsing gson file of activities received from Fitbit API call
 */

public class ActivityInfo {

    @SerializedName("activities")
    @Expose
    private List<Activity> activities = null;
    @SerializedName("goals")
    @Expose
    private Goals goals;
    @SerializedName("summary")
    @Expose
    private Summary summary;

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public Goals getGoals() {
        return goals;
    }

    public void setGoals(Goals goals) {
        this.goals = goals;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "ActivityInfo{" +
                "activities=" + activities +
                ", goals=" + goals +
                ", summary=" + summary +
                '}';
    }
}

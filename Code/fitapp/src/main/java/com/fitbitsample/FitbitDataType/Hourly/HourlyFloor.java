package com.fitbitsample.FitbitDataType.Hourly;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;


public class HourlyFloor
{
    @SerializedName("activities-floors")
    @Expose
    private List<HourlySummary> activitiesFloors = null;

    @SerializedName("activities-floors-intraday")
    @Expose
    private Intraday activitiesFloorsIntraday;

    public List<HourlySummary> getActivitiesFloors() {
        return activitiesFloors;
    }

    public void setActivitiesFloors(List<HourlySummary> activitiesFloors) {
        this.activitiesFloors = activitiesFloors;
    }

    public Intraday getActivitiesFloorsIntraday() {
        return activitiesFloorsIntraday;
    }

    public void setActivitiesFloorsIntraday(Intraday activitiesFloorsIntraday) {
        this.activitiesFloorsIntraday = activitiesFloorsIntraday;
    }
}

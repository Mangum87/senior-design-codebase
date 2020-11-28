package com.fitbitsample.FitbitDataType;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Creating a viewing adapter class for parsing gson file of distances of different activities received from Fitbit API call
 */
public class Distance {

    @SerializedName("activity")
    @Expose
    private String activity;
    @SerializedName("distance")
    @Expose
    private Double distance;

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Distance{" +
                "activity='" + activity + '\'' +
                ", distance=" + distance +
                '}';
    }
}
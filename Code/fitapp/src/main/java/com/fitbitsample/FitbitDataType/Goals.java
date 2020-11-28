package com.fitbitsample.FitbitDataType;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Creating a viewing adapter class for parsing gson file of goals received from Fitbit API call
 */

public class Goals {

    @SerializedName("activeMinutes")
    @Expose
    private Integer activeMinutes;
    @SerializedName("caloriesOut")
    @Expose
    private Integer caloriesOut;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("steps")
    @Expose
    private Integer steps;

    public Integer getActiveMinutes() {
        return activeMinutes;
    }

    public void setActiveMinutes(Integer activeMinutes) {
        this.activeMinutes = activeMinutes;
    }

    public Integer getCaloriesOut() {
        return caloriesOut;
    }

    public void setCaloriesOut(Integer caloriesOut) {
        this.caloriesOut = caloriesOut;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "Goals{" +
                "activeMinutes=" + activeMinutes +
                ", caloriesOut=" + caloriesOut +
                ", distance=" + distance +
                ", steps=" + steps +
                '}';
    }
}

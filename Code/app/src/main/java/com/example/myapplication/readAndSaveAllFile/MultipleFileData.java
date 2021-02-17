package com.example.myapplication.readAndSaveAllFile;

import java.util.ArrayList;

public class MultipleFileData {
    private String date;
    private ArrayList<String> timeStamp;
    private ArrayList<Double> calories;
    private ArrayList<Double> caloriesBMR;
    private ArrayList<Double> steps;
    private ArrayList<Double> distance;
    private ArrayList<Double> floors;
    private ArrayList<Double> elevation;
    private ArrayList<Double> minutesSedentary;
    private ArrayList<Double> minutesLightlyActive;
    private ArrayList<Double> minutesFairlyActive;
    private ArrayList<Double> minutesVeryActive;
    private ArrayList<Double> activityCalories;
    private ArrayList<Double> heartRate;

    public MultipleFileData(String date,
                            ArrayList<String> timeStamp,
                            ArrayList<Double> calories,
                            ArrayList<Double> caloriesBMR,
                            ArrayList<Double> steps,
                            ArrayList<Double> distance,
                            ArrayList<Double> floors,
                            ArrayList<Double> elevation,
                            ArrayList<Double> minutesSedentary,
                            ArrayList<Double> minutesLightlyActive,
                            ArrayList<Double> minutesFairlyActive,
                            ArrayList<Double> minutesVeryActive,
                            ArrayList<Double> activityCalories,
                            ArrayList<Double> heartRate) {
        this.date = date;
        this.timeStamp = timeStamp;
        this.calories = calories;
        this.caloriesBMR = caloriesBMR;
        this.steps = steps;
        this.distance = distance;
        this.floors = floors;
        this.elevation = elevation;
        this.minutesSedentary = minutesSedentary;
        this.minutesLightlyActive = minutesLightlyActive;
        this.minutesFairlyActive = minutesFairlyActive;
        this.minutesVeryActive = minutesVeryActive;
        this.activityCalories = activityCalories;
        this.heartRate = heartRate;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<String> getTimeStamp() {
        return timeStamp;
    }

    public ArrayList<Double> getCalories() {
        return calories;
    }

    public ArrayList<Double> getCaloriesBMR() {
        return caloriesBMR;
    }

    public ArrayList<Double> getSteps() {
        return steps;
    }

    public ArrayList<Double> getDistance() {
        return distance;
    }

    public ArrayList<Double> getFloors() {
        return floors;
    }

    public ArrayList<Double> getElevation() {
        return elevation;
    }

    public ArrayList<Double> getMinutesSedentary() {
        return minutesSedentary;
    }

    public ArrayList<Double> getMinutesLightlyActive() {
        return minutesLightlyActive;
    }

    public ArrayList<Double> getMinutesFairlyActive() {
        return minutesFairlyActive;
    }

    public ArrayList<Double> getMinutesVeryActive() {
        return minutesVeryActive;
    }

    public ArrayList<Double> getActivityCalories() {
        return activityCalories;
    }

    public ArrayList<Double> getHeartRate() {
        return heartRate;
    }
}

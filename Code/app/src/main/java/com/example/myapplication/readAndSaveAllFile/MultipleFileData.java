package com.example.myapplication.readAndSaveAllFile;

import java.util.ArrayList;


/**
 * Reads all of the intraday files locally stored
 * and stores its data.
 */
public class MultipleFileData
{
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

    public double getTotalCalories(){
        double total = 0;
        for(double val: calories){
            total += val;
        }
        return total;
    }

    public double getAverageCalories(){
        double average = getTotalCalories();
        average = average/calories.size();

        return average;
    }

    public double getTotalCaloriesBMR(){
        double total = 0;
        for(double val: caloriesBMR){
            total += val;
        }
        return total;
    }

    public double getTotalActivityCalories(){
        double total = 0;
        for(double val: activityCalories){
            total += val;
        }
        return total;
    }

    public double getTotalSteps(){
        double total = 0;
        for(double val: steps){
            total += val;
        }
        return total;
    }

    public double getTotalDistance(){
        double total = 0;
        for(double val: distance){
            total += val;
        }
        // to make the decimal pattern "#.##'
        total = Math.floor(total *100)/100;

        return total;
    }

    public double getTotalFloors(){
        double total = 0;
        for(double val: floors){
            total += val;
        }
        return total;
    }

    public double getTotalElevation(){
        double total = 0;
        for(double val: elevation){
            total += val;
        }
        return total;
    }

    public double getTotalHeartRate(){
        double total = 0;
        for(double val: heartRate){
            total += val;
        }
        return total;
    }

    public double getAverageHeartRate(){
        double value = getTotalHeartRate();
        value = value / heartRate.size();

        return value;
    }

    public int getHighHeartRate(){
        int highValue = heartRate.get(0).intValue();
        for(int i = 1; i < heartRate.size(); i++){
            if(highValue < heartRate.get(i)){
                highValue = heartRate.get(i).intValue();
            }
        }
        return highValue;
    }

    public int getLowHeartRate(){
        int lowValue = heartRate.get(0).intValue();
        for(int i = 1; i < heartRate.size(); i++){
            if(lowValue > heartRate.get(i)){
                if(heartRate.get(i).intValue() != 0){
                   lowValue = heartRate.get(i).intValue();
                }
            }
        }

        return lowValue;
    }

    public double getTotalActiveMinutes(){

        double total = getTotalMinutesSedentary();
        total += getTotalMinutesLightlyActive();
        total += getTotalMinutesFairlyActive();
        total += getTotalMinutesVeryActive();

        return total;
    }

    public double getTotalMinutesSedentary(){
        double total = 0;
        for(double val: minutesSedentary){
            total += val;
        }
        return total;
    }

    public double getTotalMinutesLightlyActive(){
        double total = 0;
        for(double val: minutesLightlyActive){
            total += val;
        }
        return total;
    }

    public double getTotalMinutesFairlyActive(){
        double total = 0;
        for(double val: minutesFairlyActive){
            total += val;
        }
        return total;
    }

    public double getTotalMinutesVeryActive(){
        double total = 0;
        for(double val: minutesVeryActive){
            total += val;
        }
        return total;
    }

    public int getHrActive(){
        int hour = ((int) getTotalActiveMinutes()) / 60;
        return hour;
    }

    public int getMinActive(){
        int min = ((int) getTotalActiveMinutes()) % 60;
        return min;
    }
}

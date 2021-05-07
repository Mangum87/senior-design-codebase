package com.example.myapplication.readAndSaveAllFile.Hourly;

import java.util.ArrayList;

//used for storing today's data
public class HourlyAllDataSets {
    private String date;
    private ArrayList<String> timeStamp;
    private ArrayList<Double> calories;
    private ArrayList<Double> steps;
    private ArrayList<Double> miles;
    private ArrayList<Double> minutesSedentary;
    private ArrayList<Double> minutesLightlyActive;
    private ArrayList<Double> minutesFairlyActive;
    private ArrayList<Double> minutesVeryActive;
    private ArrayList<Double> heartRate;

    public HourlyAllDataSets(String date,
                                  ArrayList<String> timeStamp,
                                  ArrayList<Double> calories,
                                  ArrayList<Double> steps,
                                  ArrayList<Double> miles,
                                  ArrayList<Double> minutesSedentary,
                                  ArrayList<Double> minutesLightlyActive,
                                  ArrayList<Double> minutesFairlyActive,
                                  ArrayList<Double> minutesVeryActive,
                                  ArrayList<Double> heartRate) {
        this.date = date;
        this.timeStamp = timeStamp;
        this.calories = calories;
        this.steps = steps;
        this.miles = miles;
        this.minutesSedentary = minutesSedentary;
        this.minutesLightlyActive = minutesLightlyActive;
        this.minutesFairlyActive = minutesFairlyActive;
        this.minutesVeryActive = minutesVeryActive;
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


    public ArrayList<Double> getSteps() {
        return steps;
    }

    public ArrayList<Double> getMiles() {
        return miles;
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

    public double getTotalSteps(){
        double total = 0;
        for(double val: steps){
            total += val;
        }
        return total;
    }

    public double getTotalDistance(){
        double total = 0;
        for(double val: miles){
            total += val;
        }
        // to make the decimal pattern "#.##'
        total = Math.floor(total *100)/100;

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

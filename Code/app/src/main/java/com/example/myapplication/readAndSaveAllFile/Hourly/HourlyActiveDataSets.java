package com.example.myapplication.readAndSaveAllFile.Hourly;

import java.util.ArrayList;

//this class is implemented for hourly data sets for 'active'
public class HourlyActiveDataSets {
    private String date;
    private ArrayList<Double> minutesSedentary;
    private ArrayList<Double> minutesLightlyActive;
    private ArrayList<Double> minutesFairlyActive;
    private ArrayList<Double> minutesVeryActive;

    public HourlyActiveDataSets(String date, ArrayList<Double> minutesSedentary, ArrayList<Double> minutesLightlyActive, ArrayList<Double> minutesFairlyActive, ArrayList<Double> minutesVeryActive) {
        this.date = date;
        this.minutesSedentary = minutesSedentary;
        this.minutesLightlyActive = minutesLightlyActive;
        this.minutesFairlyActive = minutesFairlyActive;
        this.minutesVeryActive = minutesVeryActive;
    }

    public String getDate() {
        return date;
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

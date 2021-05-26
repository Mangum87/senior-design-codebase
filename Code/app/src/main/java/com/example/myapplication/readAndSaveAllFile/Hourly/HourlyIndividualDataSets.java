package com.example.myapplication.readAndSaveAllFile.Hourly;

import java.util.ArrayList;

public class HourlyIndividualDataSets {

    private String date;
    private ArrayList<String> timeStamp;
    private ArrayList<Double> data;

    public HourlyIndividualDataSets(String date, ArrayList<String> timeStamp, ArrayList<Double> data) {
        this.date = date;
        this.timeStamp = timeStamp;
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<String> getTimeStamp() {
        return timeStamp;
    }

    public ArrayList<Double> getData() {
        return data;
    }

    public double getTotal(){
        double total = 0;
        for(double val: data){
            total += val;
        }
        // to make the decimal pattern "#.##'
        total = Math.floor(total *100)/100;

        return total;
    }

    public double getAverage(){
        double value = getTotal();
        value = value / data.size();

        return value;
    }

    public int getHigh(){
        int highValue = data.get(0).intValue();
        for(int i = 1; i < data.size(); i++){
            if(highValue < data.get(i)){
                highValue = data.get(i).intValue();
            }
        }
        return highValue;
    }

    public int getLow(){
        int highValue = data.get(0).intValue();
        for(int i = 1; i < data.size(); i++){
            if(highValue > data.get(i)){
                highValue = data.get(i).intValue();
            }
        }
        return highValue;
    }


}

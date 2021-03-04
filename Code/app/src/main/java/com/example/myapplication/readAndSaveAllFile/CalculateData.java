package com.example.myapplication.readAndSaveAllFile;

import java.util.ArrayList;

public  class CalculateData {

    public static double getAverage(ArrayList<Double> data) {
        double value = 0;

        for (int i = 0; i < data.size(); i++) {
            value += data.get(i);
        }
        value = value / data.size();

        return value;
    }

    public static double getTotal(ArrayList<Double> data){
        double total = 0;
        for (int i = 0; i < data.size(); i++ ){
            total += data.get(i);
        }

        // to make the decimal pattern "#.##'
        total = Math.floor(total *100)/100;

        return total;
    }

    public static int getHighValue(ArrayList<Double> data){
        int highValue = data.get(0).intValue();
        for(int i = 1; i < data.size(); i++){
            if(highValue < data.get(i)){
                highValue = data.get(i).intValue();
            }
        }
        return highValue;
    }

    public static int getLowValue(ArrayList<Double> data){
        int lowValue = data.get(0).intValue();
        for(int i = 1; i < data.size(); i++){
            if(lowValue > data.get(i)){
                lowValue = data.get(i).intValue();
            }
        }
        return lowValue;
    }

}

package com.example.myapplication.readAndSaveAllFile.Hourly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

//this class is used for all hourly data sets except for active
public class ReadHourlyData {

    private String callFrom;
    private ArrayList<String> timeStamp;
    private ArrayList<Double> data; //this is for all hourly data except active
    private File file;

    public ReadHourlyData(String callFrom, File file) {
        this.callFrom = callFrom;
        this.file = file;
        timeStamp = new ArrayList<>();
        data = new ArrayList<>();
        readFiles();
    }

    private void readFiles() {
        String lineFromFile = "";
        boolean isFirstLine = true;

        try {
            FileInputStream is = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            while ((lineFromFile = reader.readLine()) != null) {
                /** split by ',' */
                String[] tokens = lineFromFile.split(",");

                if (isFirstLine) {
                    isFirstLine = false;
                } else {
                    if(tokens.length - 1 >= 0) timeStamp.add(tokens[0].substring(0, 5));
                    switch (callFrom){
                        case "footSteps":
                            if(tokens.length - 1 >= 4) data.add(Double.parseDouble(tokens[4]));
                            break;
                        case "miles":
                            if(tokens.length - 1 >= 5) data.add(Double.parseDouble(tokens[5]));
                            break;
                        case "calories":
                            if(tokens.length - 1 >= 1) data.add(Double.parseDouble(tokens[1]));
                            break;
                        case "heartRate":
                            if(tokens.length - 1 >= 8) data.add(Double.parseDouble(tokens[8]));
                            break;
                    }
                }
            }
            reader.close(); // Close reader
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getTimeStamp() {
        return timeStamp;
    }

    public ArrayList<Double> getData() {
        return data;
    }
}

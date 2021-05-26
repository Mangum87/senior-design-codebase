package com.example.myapplication.readAndSaveAllFile.Hourly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

//this class is implemented to read data only for Active hourly data 'Active'
public class ReadHourlyDataForActive {

    private ArrayList<Double> minutesSedentary;
    private ArrayList<Double> minutesLightlyActive;
    private ArrayList<Double> minutesFairlyActive;
    private ArrayList<Double> minutesVeryActive;
    private File file;

    public ReadHourlyDataForActive(File file) {
        this.file = file;
        minutesSedentary =  new ArrayList<>();
        minutesLightlyActive = new ArrayList<>();
        minutesFairlyActive = new ArrayList<>();
        minutesVeryActive = new ArrayList<>();
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
                    if(tokens.length - 1 >= 9) minutesSedentary.add(Double.parseDouble(tokens[9]));
                    if(tokens.length - 1 >= 10) minutesLightlyActive.add(Double.parseDouble(tokens[10]));
                    if(tokens.length - 1 >= 11) minutesFairlyActive.add(Double.parseDouble(tokens[11]));
                    if(tokens.length - 1 >= 12) minutesVeryActive.add(Double.parseDouble(tokens[12]));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}

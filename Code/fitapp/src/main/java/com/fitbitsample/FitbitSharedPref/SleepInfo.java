package com.fitbitsample.FitbitSharedPref;

public class SleepInfo
{
    private String date;
    private int duration;
    private int efficiency;
    private int totalMinutes;

    public SleepInfo(String date, int duration, int efficiency, int minutes)
    {
        this.date = date;
        this.duration = duration;
        this.efficiency = efficiency;
        this.totalMinutes = minutes;
    }


    public String getDate() {
        return date;
    }

    public int getDuration() {
        return duration;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }
}

package com.fitbitsample.FitbitSharedPref;

import com.fitbitsample.FitbitDataType.SleepData.Data;

import java.util.ArrayList;

public class SleepInfo
{
    private String date;
    private int duration;
    private int efficiency;
    private int minutesAsleep;
    private int minutesAwake;
    private int minuteToFallAsleep;
    private String time;

    private int deepCount;
    private int deepAvg;
    private int deepMinutes;

    private int lightCount;
    private int lightAvg;
    private int lightMinutes;

    private int remCount;
    private int remAvg;
    private int remMinutes;

    private int wakeCount;
    private int wakeAvg;
    private int wakeMinutes;

    private ArrayList<Data> data;


    public SleepInfo()
    {
        this.data = new ArrayList<Data>(10);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    public int getMinutesAsleep() {
        return minutesAsleep;
    }

    public void setMinutesAsleep(int minutesAsleep) {
        this.minutesAsleep = minutesAsleep;
    }

    public int getMinutesAwake() {
        return minutesAwake;
    }

    public void setMinutesAwake(int minutesAwake) {
        this.minutesAwake = minutesAwake;
    }

    public int getMinuteToFallAsleep() {
        return minuteToFallAsleep;
    }

    public void setMinuteToFallAsleep(int minuteToFallAsleep) {
        this.minuteToFallAsleep = minuteToFallAsleep;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDeepCount() {
        return deepCount;
    }

    public void setDeepCount(int deepCount) {
        this.deepCount = deepCount;
    }

    public int getDeepAvg() {
        return deepAvg;
    }

    public void setDeepAvg(int deepAvg) {
        this.deepAvg = deepAvg;
    }

    public int getDeepMinutes() {
        return deepMinutes;
    }

    public void setDeepMinutes(int deepMinutes) {
        this.deepMinutes = deepMinutes;
    }

    public int getLightCount() {
        return lightCount;
    }

    public void setLightCount(int lightCount) {
        this.lightCount = lightCount;
    }

    public int getLightAvg() {
        return lightAvg;
    }

    public void setLightAvg(int lightAvg) {
        this.lightAvg = lightAvg;
    }

    public int getLightMinutes() {
        return lightMinutes;
    }

    public void setLightMinutes(int lightMinutes) {
        this.lightMinutes = lightMinutes;
    }

    public int getRemCount() {
        return remCount;
    }

    public void setRemCount(int remCount) {
        this.remCount = remCount;
    }

    public int getRemAvg() {
        return remAvg;
    }

    public void setRemAvg(int remAvg) {
        this.remAvg = remAvg;
    }

    public int getRemMinutes() {
        return remMinutes;
    }

    public void setRemMinutes(int remMinutes) {
        this.remMinutes = remMinutes;
    }

    public int getWakeCount() {
        return wakeCount;
    }

    public void setWakeCount(int wakeCount) {
        this.wakeCount = wakeCount;
    }

    public int getWakeAvg() {
        return wakeAvg;
    }

    public void setWakeAvg(int wakeAvg) {
        this.wakeAvg = wakeAvg;
    }

    public int getWakeMinutes() {
        return wakeMinutes;
    }

    public void setWakeMinutes(int wakeMinutes) {
        this.wakeMinutes = wakeMinutes;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    /**
     * Adds a Data object to the list.
     * @param level State name (rem, wake, light, deep)
     * @param seconds Length of state
     * @param time Start time of state
     */
    public void addData(String level, int seconds, String time)
    {
        this.data.add(new Data(level, seconds, time));
    }
}

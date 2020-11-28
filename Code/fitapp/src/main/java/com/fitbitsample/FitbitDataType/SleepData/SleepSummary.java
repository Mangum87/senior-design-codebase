package com.fitbitsample.FitbitDataType.SleepData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SleepSummary
{
    @SerializedName("totalMinutesAsleep")
    @Expose
    private Integer totalMinutesAsleep;
    @SerializedName("totalSleepRecords")
    @Expose
    private Integer totalSleepRecords;
    @SerializedName("totalTimeInBed")
    @Expose
    private Integer totalTimeInBed;

    public Integer getTotalMinutesAsleep() {
        return totalMinutesAsleep;
    }

    public void setTotalMinutesAsleep(Integer totalMinutesAsleep) {
        this.totalMinutesAsleep = totalMinutesAsleep;
    }

    public Integer getTotalSleepRecords() {
        return totalSleepRecords;
    }

    public void setTotalSleepRecords(Integer totalSleepRecords) {
        this.totalSleepRecords = totalSleepRecords;
    }

    public Integer getTotalTimeInBed() {
        return totalTimeInBed;
    }

    public void setTotalTimeInBed(Integer totalTimeInBed) {
        this.totalTimeInBed = totalTimeInBed;
    }


    @Override
    public String toString()
    {
        return "totalMinutesAsleep: " + totalMinutesAsleep + ",\n" +
                "totalSleepRecords: " + totalSleepRecords + ",\n" +
                "totalTimeInBed: " + totalTimeInBed + "\n";
    }
}

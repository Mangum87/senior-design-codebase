package com.fitbitsample.FitbitDataType.SleepData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data
{
    /*@SerializedName("datetime")
    @Expose
    private String datetime;*/
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("seconds")
    @Expose
    private Integer seconds;
    @SerializedName("dateTime")
    @Expose
    private String dateTime;

    /*public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }*/

    public Data(String level, int seconds, String time)
    {
        this.level = level;
        this.seconds = seconds;
        this.dateTime = time;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString()
    {
        /*return "datetime: " + datetime + ",\n" +
                "level: " + level + ",\n" +
                "seconds: " + seconds + ",\n" +
                "dateTime: " + dateTime;*/


        return "level: " + level + ",\n" +
                "seconds: " + seconds + ",\n" +
                "dateTime: " + dateTime;
    }
}

package com.fitbitsample.FitbitDataType.Hourly;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Holds intraday activity data. All calls
 * use this class, but not all attributes are used.<br /><br />
 * Below are the attributes that the various
 * classes use:
 * <ul>
 * <li>Calorie: Level, Value, Time, Mets</li>
 * <li>Step: Time, Value</li>
 * <li>Heart, Floors, Elevation, and Distance: Time, Value</li>
 * </ul>
 */
public class Dataset
{
    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("value")
    @Expose
    private Float value;

    @SerializedName("level")
    @Expose
    private Integer level;

    @SerializedName("mets")
    @Expose
    private Integer mets;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getMets() {
        return mets;
    }

    public void setMets(Integer mets) {
        this.mets = mets;
    }
}

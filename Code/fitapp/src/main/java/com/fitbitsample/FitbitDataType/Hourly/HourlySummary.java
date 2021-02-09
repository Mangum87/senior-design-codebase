package com.fitbitsample.FitbitDataType.Hourly;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HourlySummary
{
    @SerializedName("dateTime")
    @Expose
    private String dateTime;

    @SerializedName("value")
    @Expose
    private Float total;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Float getValue() {
        return total;
    }

    public void setValue(Float value) {
        this.total = value;
    }
}

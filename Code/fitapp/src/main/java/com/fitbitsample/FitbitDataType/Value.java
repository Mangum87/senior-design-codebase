package com.fitbitsample.FitbitDataType;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
/**
 * Creating a viewing adapter class for parsing gson file heart data received from Fitbit API call
 */

public class Value
{
    @SerializedName("customHeartRateZones")
    @Expose
    private List<Object> customHeartRateZones = null;
    @SerializedName("heartRateZones")
    @Expose
    private List<HeartRateZone> heartRateZones = null;
    @SerializedName("restingHeartRate")
    @Expose
    private Integer restingHeartRate = 0;

    public List<Object> getCustomHeartRateZones() {
        return customHeartRateZones;
    }

    public void setCustomHeartRateZones(List<Object> customHeartRateZones) {
        this.customHeartRateZones = customHeartRateZones;
    }

    public List<HeartRateZone> getHeartRateZones() {
        return heartRateZones;
    }

    public void setHeartRateZones(List<HeartRateZone> heartRateZones) {
        this.heartRateZones = heartRateZones;
    }

    public Integer getRestingHeartRate() {
        return restingHeartRate;
    }

    public void setRestingHeartRate(Integer restingHeartRate) {
        this.restingHeartRate = restingHeartRate;
    }

    @Override
    public String toString() {
        return "Value{" +
                "customHeartRateZones=" + customHeartRateZones +
                ", \n heartRateZones=" + heartRateZones +
                ", \n restingHeartRate=" + restingHeartRate +
                '}';
    }
}

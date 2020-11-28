package com.fitbitsample.FitbitDataType;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Data about a user's FitBit device.
 */
public class Device
{
    @SerializedName("battery")
    @Expose
    private String battery;

    @SerializedName("deviceVersion")
    @Expose
    private String version;

    @SerializedName("lastSyncTime")
    @Expose
    private String lastSync;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("id")
    @Expose
    private String ID;



    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLastSync() {
        return lastSync;
    }

    public void setLastSync(String lastSync) {
        this.lastSync = lastSync;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "Device{" +
                "ID=" + ID +
                ", battery=" + battery +
                ", version=" + version +
                ", lastSync=" + lastSync +
                ", type=" + type +
                "}";
    }
}

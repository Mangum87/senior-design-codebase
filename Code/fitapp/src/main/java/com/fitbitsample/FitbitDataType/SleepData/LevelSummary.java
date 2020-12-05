package com.fitbitsample.FitbitDataType.SleepData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LevelSummary
{
    @SerializedName("deep")
    @Expose
    private SummaryData deep;
    @SerializedName("light")
    @Expose
    private SummaryData light;
    @SerializedName("rem")
    @Expose
    private SummaryData rem;
    @SerializedName("wake")
    @Expose
    private SummaryData wake;
    /*@SerializedName("asleep")
    @Expose
    private ShortSummary asleep;
    @SerializedName("awake")
    @Expose
    private ShortSummary awake;
    @SerializedName("restless")
    @Expose
    private ShortSummary restless;*/

    public SummaryData getDeep() {
        return deep;
    }

    public void setDeep(SummaryData deep) {
        this.deep = deep;
    }

    public SummaryData getLight() {
        return light;
    }

    public void setLight(SummaryData light) {
        this.light = light;
    }

    public SummaryData getRem() {
        return rem;
    }

    public void setRem(SummaryData rem) {
        this.rem = rem;
    }

    public SummaryData getWake() {
        return wake;
    }

    public void setWake(SummaryData wake) {
        this.wake = wake;
    }

    /*public ShortSummary getAsleep() {
        return asleep;
    }

    public void setAsleep(ShortSummary asleep) {
        this.asleep = asleep;
    }

    public ShortSummary getAwake() {
        return awake;
    }

    public void setAwake(ShortSummary awake) {
        this.awake = awake;
    }

    public ShortSummary getRestless() {
        return restless;
    }

    public void setRestless(ShortSummary restless) {
        this.restless = restless;
    }*/

    @Override
    public String toString()
    {
        /*return "deep: {\n" + deep + "\n},\n" +
                "light: {\n" + light + "\n},\n" +
                "rem: {\n" + rem + "\n},\n" +
                "wake: {\n" + wake + "\n},\n" +
                "asleep: {\n" + asleep + "\n},\n" +
                "awake: {\n" + awake + "\n},\n" +
                "restless: {\n" + restless + "\n}";*/


        return "deep: {\n" + deep + "\n},\n" +
                "light: {\n" + light + "\n},\n" +
                "rem: {\n" + rem + "\n},\n" +
                "wake: {\n" + wake + "\n}";
    }
}

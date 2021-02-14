package com.fitbitsample.FitbitSharedPref;

import com.fitbitsample.FitbitDataType.Hourly.Dataset;
import java.util.ArrayList;

/**
 * Saves the summarize data from fitbit website,
 * each time GetHrModel is triggered the data
 * gets saved in the preference and updates the local cache.
 */
public class HeartRateInfo
{
    private int restingRate;

    // Out of Range metrics
    private float rangeCalorie;
    private int rangeMin;
    private int rangeMax;
    private int rangeMinutes;

    // Fat Burn metrics
    private float fatCalorie;
    private int fatMin;
    private int fatMax;
    private int fatMinutes;

    // Cardio metrics
    private float cardioCalorie;
    private int cardioMin;
    private int cardioMax;
    private int cardioMinutes;

    // Peak metrics
    private float peakCalorie;
    private int peakMin;
    private int peakMax;
    private int peakMinutes;

    private ArrayList<Dataset> data;


    public HeartRateInfo(int rest)
    {
        this.restingRate = rest;
        this.data = new ArrayList<Dataset>(96);
    }


    /**
     * Sets the values for the Out of Range metrics.
     * @param cal Calories
     * @param min Minimum
     * @param max Maximum
     * @param minutes Minutes in range
     */
    public void setRange(float cal, int min, int max, int minutes)
    {
        this.rangeCalorie = cal;
        this.rangeMin = min;
        this.rangeMax = max;
        this.rangeMinutes = minutes;
    }



    /**
     * Sets the values for the Fat Burn metrics.
     * @param cal Calories
     * @param min Minimum
     * @param max Maximum
     * @param minutes Minutes in range
     */
    public void setFat(float cal, int min, int max, int minutes)
    {
        this.fatCalorie = cal;
        this.fatMin = min;
        this.fatMax = max;
        this.fatMinutes = minutes;
    }



    /**
     * Sets the values for the Cardio metrics.
     * @param cal Calories
     * @param min Minimum
     * @param max Maximum
     * @param minutes Minutes in range
     */
    public void setCardio(float cal, int min, int max, int minutes)
    {
        this.cardioCalorie = cal;
        this.cardioMin = min;
        this.cardioMax = max;
        this.cardioMinutes = minutes;
    }




    /**
     * Sets the values for the Peak metrics.
     * @param cal Calories
     * @param min Minimum
     * @param max Maximum
     * @param minutes Minutes in range
     */
    public void setPeak(float cal, int min, int max, int minutes)
    {
        this.peakCalorie = cal;
        this.peakMin = min;
        this.peakMax = max;
        this.peakMinutes = minutes;
    }


    public int getRestingRate() {
        return restingRate;
    }

    public float getRangeCalorie() {
        return rangeCalorie;
    }

    public int getRangeMin() {
        return rangeMin;
    }

    public int getRangeMax() {
        return rangeMax;
    }

    public int getRangeMinutes() {
        return rangeMinutes;
    }

    public float getFatCalorie() {
        return fatCalorie;
    }

    public int getFatMin() {
        return fatMin;
    }

    public int getFatMax() {
        return fatMax;
    }

    public int getFatMinutes() {
        return fatMinutes;
    }

    public float getCardioCalorie() {
        return cardioCalorie;
    }

    public int getCardioMin() {
        return cardioMin;
    }

    public int getCardioMax() {
        return cardioMax;
    }

    public int getCardioMinutes() {
        return cardioMinutes;
    }

    public float getPeakCalorie() {
        return peakCalorie;
    }

    public int getPeakMin() {
        return peakMin;
    }

    public int getPeakMax() {
        return peakMax;
    }

    public int getPeakMinutes() {
        return peakMinutes;
    }

    public ArrayList<Dataset> getData() {
        return data;
    }


    /**
     * Add a set of data to the list.
     * @param set Set of data
     */
    public void addSet(Dataset set)
    {
        this.data.add(set);
    }
}

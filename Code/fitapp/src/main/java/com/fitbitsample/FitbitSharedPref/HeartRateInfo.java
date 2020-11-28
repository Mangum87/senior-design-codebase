package com.fitbitsample.FitbitSharedPref;

/**
 * Saves the summarize data from fitbit website,
 * each time GetHrModel is triggered the data
 * gets saved in the preference and updates the local cache.
 */
public class HeartRateInfo
{
    private int restingRate;

    // Out of Range metrics
    private int rangeCalorie;
    private int rangeMin;
    private int rangeMax;
    private int rangeMinutes;

    // Fat Burn metrics
    private int fatCalorie;
    private int fatMin;
    private int fatMax;
    private int fatMinutes;

    // Cardio metrics
    private int cardioCalorie;
    private int cardioMin;
    private int cardioMax;
    private int cardioMinutes;

    // Peak metrics
    private int peakCalorie;
    private int peakMin;
    private int peakMax;
    private int peakMinutes;


    public HeartRateInfo(int rest)
    {
        this.restingRate = rest;
    }


    /**
     * Sets the values for the Out of Range metrics.
     * @param cal Calories
     * @param min Minimum
     * @param max Maximum
     * @param minutes Minutes in range
     */
    public void setRange(int cal, int min, int max, int minutes)
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
    public void setFat(int cal, int min, int max, int minutes)
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
    public void setCardio(int cal, int min, int max, int minutes)
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
    public void setPeak(int cal, int min, int max, int minutes)
    {
        this.peakCalorie = cal;
        this.peakMin = min;
        this.peakMax = max;
        this.peakMinutes = minutes;
    }


    public int getRestingRate() {
        return restingRate;
    }

    public int getRangeCalorie() {
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

    public int getFatCalorie() {
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

    public int getCardioCalorie() {
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

    public int getPeakCalorie() {
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
}

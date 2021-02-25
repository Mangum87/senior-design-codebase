package com.example.myapplication.readAndSaveAllFile.Summary;


/**
 * Contains all of the activity summary data
 * for a single day.
 */
public class SummaryActivity
{
    private int activeScore;
    private int totalCalories;
    private double trackerDistance;
    private double loggedDistance;
    private int duration; // In milliseconds
    private int restingHR;
    private CalorieInfo outRange; // Out of range
    private CalorieInfo fatBurn;
    private CalorieInfo cardio;
    private CalorieInfo peak;


    /**
     * Takes a token array from summary file read.
     * @param token Tokens from summary file
     */
    public SummaryActivity(String[] token)
    {
        this.activeScore = Integer.parseInt(token[2]);
        this.totalCalories = Integer.parseInt(token[3]);
        this.trackerDistance = Double.parseDouble(token[4]);
        this.loggedDistance = Double.parseDouble(token[5]);
        this.duration = Integer.parseInt(token[6]);
        this.restingHR = Integer.parseInt(token[9]);

        setCardio(token);
        setFatBurn(token);
        setOutRange(token);
        setPeak(token);
    }


    public CalorieInfo getOutRange() {
        return outRange;
    }

    public void setOutRange(String[] token)
    {
        double c = Double.parseDouble(token[11]);
        int min = Integer.parseInt(token[12]);
        int max = Integer.parseInt(token[13]);
        int t = Integer.parseInt(token[14]);

        this.outRange = new CalorieInfo(c, min, max, t);
    }

    public CalorieInfo getFatBurn() {
        return fatBurn;
    }

    public void setFatBurn(String[] token)
    {
        double c = Double.parseDouble(token[16]);
        int min = Integer.parseInt(token[17]);
        int max = Integer.parseInt(token[18]);
        int t = Integer.parseInt(token[19]);

        this.fatBurn = new CalorieInfo(c, min, max, t);
    }

    public CalorieInfo getCardio() {
        return cardio;
    }

    public void setCardio(String[] token)
    {
        double c = Double.parseDouble(token[21]);
        int min = Integer.parseInt(token[22]);
        int max = Integer.parseInt(token[23]);
        int t = Integer.parseInt(token[24]);

        this.cardio = new CalorieInfo(c, min, max, t);
    }

    public CalorieInfo getPeak() {
        return peak;
    }

    public void setPeak(String[] token)
    {
        double c = Double.parseDouble(token[26]);
        int min = Integer.parseInt(token[27]);
        int max = Integer.parseInt(token[28]);
        int t = Integer.parseInt(token[29]);

        this.peak = new CalorieInfo(c, min, max, t);
    }

    public int getActiveScore() {
        return activeScore;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public double getTrackerDistance() {
        return trackerDistance;
    }

    public double getLoggedDistance() {
        return loggedDistance;
    }

    public int getDuration() {
        return duration;
    }

    public int getRestingHR() {
        return restingHR;
    }
}

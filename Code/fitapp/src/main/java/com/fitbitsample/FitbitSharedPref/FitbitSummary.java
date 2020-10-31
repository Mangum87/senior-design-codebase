package com.fitbitsample.FitbitSharedPref;

/**
 * Saves the summarize data from fitbit website,
 * each time GetActivityModel is triggered the running total of the data
 * gets saved in the preference and updates the local cache
 */
public class FitbitSummary {
    private Integer activeScore, activityCalories, caloriesBMR, caloriesOut, fairlyActiveMinutes, lightlyActiveMinutes, marginalCalories, sedentaryMinutes, steps, veryActiveMinutes;
    private String total, tracker, loggedActivities, veryActive, moderatelyActive, lightlyActive, sedentaryActive;

    public FitbitSummary(Integer activeScore, Integer activityCalories, Integer caloriesBMR, Integer caloriesOut, Integer fairlyActiveMinutes, Integer lightlyActiveMinutes, Integer marginalCalories, Integer sedentaryMinutes, Integer steps, Integer veryActiveMinutes, String total, String tracker, String loggedActivities, String veryActive, String moderatelyActive, String lightlyActive, String sedentaryActive) {
        this.activeScore = activeScore;
        this.activityCalories = activityCalories;
        this.caloriesBMR = caloriesBMR;
        this.caloriesOut = caloriesOut;
        this.fairlyActiveMinutes = fairlyActiveMinutes;
        this.lightlyActiveMinutes = lightlyActiveMinutes;
        this.marginalCalories = marginalCalories;
        this.sedentaryMinutes = sedentaryMinutes;
        this.steps = steps;
        this.veryActiveMinutes = veryActiveMinutes;
        this.total = total;
        this.tracker = tracker;
        this.loggedActivities = loggedActivities;
        this.veryActive = veryActive;
        this.moderatelyActive = moderatelyActive;
        this.lightlyActive = lightlyActive;
        this.sedentaryActive = sedentaryActive;
    }

    public Integer getActiveScore() {
        return activeScore;
    }

    public void setActiveScore(Integer activeScore) {
        this.activeScore = activeScore;
    }

    public Integer getActivityCalories() {
        return activityCalories;
    }

    public void setActivityCalories(Integer activityCalories) {
        this.activityCalories = activityCalories;
    }

    public Integer getCaloriesBMR() {
        return caloriesBMR;
    }

    public void setCaloriesBMR(Integer caloriesBMR) {
        this.caloriesBMR = caloriesBMR;
    }

    public Integer getCaloriesOut() {
        return caloriesOut;
    }

    public void setCaloriesOut(Integer caloriesOut) {
        this.caloriesOut = caloriesOut;
    }

    public Integer getFairlyActiveMinutes() {
        return fairlyActiveMinutes;
    }

    public void setFairlyActiveMinutes(Integer fairlyActiveMinutes) {
        this.fairlyActiveMinutes = fairlyActiveMinutes;
    }

    public Integer getLightlyActiveMinutes() {
        return lightlyActiveMinutes;
    }

    public void setLightlyActiveMinutes(Integer lightlyActiveMinutes) {
        this.lightlyActiveMinutes = lightlyActiveMinutes;
    }

    public Integer getMarginalCalories() {
        return marginalCalories;
    }

    public void setMarginalCalories(Integer marginalCalories) {
        this.marginalCalories = marginalCalories;
    }

    public Integer getSedentaryMinutes() {
        return sedentaryMinutes;
    }

    public void setSedentaryMinutes(Integer sedentaryMinutes) {
        this.sedentaryMinutes = sedentaryMinutes;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public Integer getVeryActiveMinutes() {
        return veryActiveMinutes;
    }

    public void setVeryActiveMinutes(Integer veryActiveMinutes) {
        this.veryActiveMinutes = veryActiveMinutes;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTracker() {
        return tracker;
    }

    public void setTracker(String tracker) {
        this.tracker = tracker;
    }

    public String getLoggedActivities() {
        return loggedActivities;
    }

    public void setLoggedActivities(String loggedActivities) {
        this.loggedActivities = loggedActivities;
    }

    public String getVeryActive() {
        return veryActive;
    }

    public void setVeryActive(String veryActive) {
        this.veryActive = veryActive;
    }

    public String getModeratelyActive() {
        return moderatelyActive;
    }

    public void setModeratelyActive(String moderatelyActive) {
        this.moderatelyActive = moderatelyActive;
    }

    public String getLightlyActive() {
        return lightlyActive;
    }

    public void setLightlyActive(String lightlyActive) {
        this.lightlyActive = lightlyActive;
    }

    public String getSedentaryActive() {
        return sedentaryActive;
    }

    public void setSedentaryActive(String sedentaryActive) {
        this.sedentaryActive = sedentaryActive;
    }
}

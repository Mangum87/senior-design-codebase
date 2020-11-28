package com.fitbitsample.FitbitDataType;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Creating a viewing adapter class for parsing gson file of user file received from Fitbit API call
 */

public class User {

    @SerializedName("age")
    @Expose
    private Integer age;

    @SerializedName("averageDailySteps")
    @Expose
    private Integer averageDailySteps;

    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;

    @SerializedName("displayNameSetting")
    @Expose
    private String displayNameSetting;

    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("height")
    @Expose
    private Double height;

    @SerializedName("startDayOfWeek")
    @Expose
    private String startDayOfWeek;

    @SerializedName("strideLengthRunning")
    @Expose
    private Double strideLengthRunning;

    @SerializedName("strideLengthWalking")
    @Expose
    private Double strideLengthWalking;

    @SerializedName("weight")
    @Expose
    private Double weight;

    @SerializedName("weightUnit")
    @Expose
    private String weightUnit;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAverageDailySteps() {
        return averageDailySteps;
    }

    public void setAverageDailySteps(Integer averageDailySteps) {
        this.averageDailySteps = averageDailySteps;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDisplayNameSetting() {
        return displayNameSetting;
    }

    public void setDisplayNameSetting(String displayNameSetting) {
        this.displayNameSetting = displayNameSetting;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getStartDayOfWeek() {
        return startDayOfWeek;
    }

    public void setStartDayOfWeek(String startDayOfWeek) {
        this.startDayOfWeek = startDayOfWeek;
    }

    public Double getStrideLengthRunning() {
        return strideLengthRunning;
    }

    public void setStrideLengthRunning(Double strideLengthRunning) {
        this.strideLengthRunning = strideLengthRunning;
    }

    public Double getStrideLengthWalking() {
        return strideLengthWalking;
    }

    public void setStrideLengthWalking(Double strideLengthWalking) {
        this.strideLengthWalking = strideLengthWalking;
    }
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "{" +
                "age=" + age +
                ", \n averageDailySteps=" + averageDailySteps +
                ", \ndateOfBirth='" + dateOfBirth + '\'' +
                ", \nfullName='" + fullName + '\'' +
                ", \ngender='" + gender + '\'' +
                ", \nheight=" + height +
                ", \nstartDayOfWeek='" + startDayOfWeek + '\'' +
                ", \nstrideLengthRunning=" + strideLengthRunning +
                ", \nstrideLengthWalking=" + strideLengthWalking +
                ", \nweight=" + weight +
                '}';
    }
}

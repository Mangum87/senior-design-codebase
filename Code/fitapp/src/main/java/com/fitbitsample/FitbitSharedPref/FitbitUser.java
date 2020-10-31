package com.fitbitsample.FitbitSharedPref;
/**
 * Saves the data from fitbit website,
 * each time GetUserModel is triggered the data
 * gets saved in the preference and updates the local cache
 */
public class FitbitUser {
    private String dateOfBirth, fullName, gender, height, weight, age;

    public FitbitUser(String dateOfBirth, String fullName, String gender, String height, String weight, String age) {
        this.dateOfBirth = dateOfBirth;
        this.fullName = fullName;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.age = age;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }
}

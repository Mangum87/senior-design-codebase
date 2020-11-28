package com.fitbitsample.FitbitDataType;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Creating a viewing adapter class for parsing gson file of userinfo received from Fitbit API call
 */
public class UserInfo {

    @SerializedName("user")
    @Expose
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "user=" + user +
                '}';
    }
}

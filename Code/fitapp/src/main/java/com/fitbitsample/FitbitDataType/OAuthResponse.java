package com.fitbitsample.FitbitDataType;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;
/**
 * Creating a viewing adapter class for parsing gson response for OAUTH 2.0 received from Fitbit API call
 */
public class OAuthResponse {

    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("expires_in")
    @Expose
    private Integer expiresIn;
    @SerializedName("refresh_token")
    @Expose
    private String refreshToken;
    @SerializedName("scope")
    @Expose
    private String scope;
    @SerializedName("token_type")
    @Expose
    private String tokenType;
    @SerializedName("user_id")
    @Expose
    private String userId;

    /**
     * The time that the OAuth token will expire.
     */
    private Date expireTime;


    /**
     * Sets the expiration time of the OAuth token to
     * be in current time + expires_in seconds.
     * Default response is 8 hours.
     */
    public void setExpireTime()
    {
        // Set the expiration time as long from UNIX time.
        // Set to expire in expiresIn hours.
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, this.expiresIn);
        this.expireTime = new Date(cal.getTime().getTime());
    }

    /**
     * Returns the date of when the OAuth token expires.
     * @return Date of expiration in seconds
     */
    public long getExpireTime()
    {
        return this.expireTime.getTime();
    }


    /**
     * Checks if the token is now or will expire
     * in the next 60 seconds.
     * @return True if expired, else false
     */
    public boolean isTokenExpired()
    {
        Date now = new Date(); // Get current time
        final int buffer = 60000; // 60 seconds

        if(now.getTime() <= (getExpireTime() - buffer)) // Is current time within 60 seconds of expiration?
            return false;
        else
            return true;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}


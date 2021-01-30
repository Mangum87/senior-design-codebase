package com.fitbitsample.FitbitApiHandling;

import com.fitbitsample.FitbitDataType.HeartRate;
import com.fitbitsample.FitbitDataType.Hourly.HourlyCalorie;
import com.fitbitsample.FitbitDataType.Hourly.HourlyDistance;
import com.fitbitsample.FitbitDataType.Hourly.HourlyElevation;
import com.fitbitsample.FitbitDataType.Hourly.HourlyFloor;
import com.fitbitsample.FitbitDataType.Hourly.HourlyStep;
import com.fitbitsample.FitbitDataType.OAuthResponse;
import com.fitbitsample.FitbitDataType.ActivityInfo;
import com.fitbitsample.FitbitDataType.SleepData.Sleep;
import com.fitbitsample.FitbitDataType.UserInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Interface for FitBit API calls.
 */
public interface FitbitAPIcalls
{
    /**
     * Refreshes the OAuth token.
     * @param grant_type Needs to be "refresh_token"
     * @param refresh_token Refresh token from OAuth
     * @return Call to save new OAuth
     */
    @FormUrlEncoded
    @POST("oauth2/token?")
    Call<OAuthResponse> refreshToken(@Field("grant_type") String grant_type, @Field("refresh_token") String refresh_token);


    /**
     * Retrieves OAuth token.
     * @param client_id ID of dev account
     * @param grantType Should be "authorization_code"
     * @param redirectUri Callback URL
     * @param code User ID code
     * @return Call to retrieve token
     */
    @FormUrlEncoded
    @POST("oauth2/token?")
    Call<OAuthResponse> getAccessToken(@Field("client_id") String client_id, @Field("grant_type") String grantType,
                                       @Field("redirect_uri") String redirectUri, @Field("code") String code);

    /**
     * Gets the user profile information.
     * @param userId User ID code
     * @return Call to get user info
     */
    @GET("1/user/{userId}/profile.json")
    Call<UserInfo> getUserProfile(@Path("userId") String userId);


    /**
     * Gets a user's activity data.
     * @param userId User ID code
     * @param date Date of activity
     * @return Call for user activity
     */
    @GET("1/user/{userId}/activities/date/{date}.json")
    Call<ActivityInfo> getActivitiesByDate(@Path("userId") String userId, @Path("date") String date);


    /**
     * Gets user heart activity for a single day.
     * @param userId User ID code
     * @param date Date of activity
     * @param period Time period
     * @return Call for range of heart dates
     */
    @GET("1/user/{userId}/activities/heart/date/{date}/{period}.json")
    Call<HeartRate> getHrByRange(@Path("userId") String userId, @Path("date") String date, @Path("period") String period);



    /**
     * Gets the user's sleep patterns for given day of user.
     * @param date Date of sleep
     * @return Call for sleep activity
     */
    @GET("1.2/user/{userId}/sleep/date/{date}.json")
    Call<Sleep> getSleep(@Path("userId") String id, @Path("date") String date);


    /**
     * Gets one day calories in 15 min increments.
     * @param id User ID
     * @param date Date to retrieve
     * @return Call for calorie activity
     */
    @GET("1/user/{id}/activities/calories/date/{date}/1d/15min.json")
    Call<HourlyCalorie> getHourlyCalorie(@Path("id") String id, @Path("date") String date);


    /**
     * Gets one day steps in 15 min increments.
     * @param id User ID
     * @param date Date to retrieve
     * @return Call for step activity
     */
    @GET("1/user/{id}/activities/steps/date/{date}/1d/15min.json")
    Call<HourlyStep> getHourlyStep(@Path("id") String id, @Path("date") String date);


    /**
     * Gets the one day heart information in 15 min increments.
     * @param id User ID
     * @param date Date to request
     * @return Call for heart activity
     */
    @GET("1/user/{id}/activities/heart/date/{date}/1d/15min.json")
    Call<HeartRate> getHourlyHeart(@Path("id") String id, @Path("date") String date);



    /**
     * Gets the one day distance information in 15 min increments.
     * @param id User ID
     * @param date Date to request
     * @return Call for distance activity
     */
    @GET("1/user/{id}/activities/distance/date/{date}/1d/15min.json")
    Call<HourlyDistance> getHourlyDistance(@Path("id") String id, @Path("date") String date);



    /**
     * Gets the one day floors information in 15 min increments.
     * @param id User ID
     * @param date Date to request
     * @return Call for floors activity
     */
    @GET("1/user/{id}/activities/floors/date/{date}/1d/15min.json")
    Call<HourlyFloor> getHourlyFloor(@Path("id") String id, @Path("date") String date);



    /**
     * Gets the one day elevation information in 15 min increments.
     * @param id User ID
     * @param date Date to request
     * @return Call for elevation activity
     */
    @GET("1/user/{id}/activities/elevation/date/{date}/1d/15min.json")
    Call<HourlyElevation> getHourlyElevation(@Path("id") String id, @Path("date") String date);
}

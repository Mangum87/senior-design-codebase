package com.example.myapplication.LoginStuff;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Holds the API calls for communicating with the AWS server.
 */
public interface Api
{
    // TO learn more about FormUrlEncoded
    // https://square.github.io/retrofit/2.x/retrofit/retrofit2/http/FormUrlEncoded.html

    /**
     * Calls {Server URL}/auth/login<p />
     * Method: POST<p />
     * Data: {<p />
     *          "username":{input}<p />
     *          "password":{input}<p />
     *       }
     * @param username Username of user
     * @param password Password of user
     * @return Instance of a Retrofit Call object.
     */
    @FormUrlEncoded
    @POST("auth/login") //using the login endpoint http://ec2-54-214-218-104.us-west-2.compute.amazonaws.com/auth/login
    Call<LoginResponse> userLogin(
            @Field("username") String username,
            @Field("password") String password
    );






}

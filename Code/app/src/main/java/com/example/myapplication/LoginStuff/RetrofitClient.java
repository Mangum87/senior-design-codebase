package com.example.myapplication.LoginStuff;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class creates the means to connect to the AWS
 * instance that FitBit data is uploaded to.<p />
 * Get the instance of this class to make API calls to
 * the server. Uses Gson library to prepare calls and responses.<p />
 * Define base url of our API.
 * endpoint=http://ec2-52-15-232-241.us-east-2.compute.amazonaws.com:5000/
 * Retrofit Singleton Client is used to handle many parallel api request.
 */
public class RetrofitClient
{
    /**
     * The base url for all calls to be added to for AWS.
     */
    public static final String BASE_URL = "http://3.19.30.128:5000/"; // for EC2 server
//    public static final String BASE_URL = "http://192.168.1.234:5000/"; // for local server
    private static RetrofitClient mInstance;
    private Retrofit retrofit;
    //create private constructor and initialize the Retrofit object
    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Returns the instance of this class.
     * If one does not exist, one will be made
     * before returning. Is thread safe.
     * @return RetrofitClient instance
     */
    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    /**
     * Create connection to the API calls.
     * @return API object
     */
    public Api getApi()
    {
        return retrofit.create(Api.class);
    }
}

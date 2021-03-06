package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.LoginStuff.LoginResponse;
import com.example.myapplication.LoginStuff.User;
/*
SharedPreferenceManager is used to save the user data in out local storage so that
until and unless user don't hit the signout button, user don't have to sign in again even after the
app is closed. Also, we have used shared preferences to save user information that was
used during registration so that we can create a user profile.
learn more:https://developer.android.com/training/data-storage/shared-preferences
 */
public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "my_shared_preff";

    private static SharedPrefManager mInstance;
    private Context mCtx; //to handle Shared Preferences we need context object

    SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }
    //save single instance
    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) //object is not yet created
        {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }

    /*
    Now create  saveUser()  method that will save user inside the shared preferences.
     */
    public void saveUser(User user) {//User is the class created to get the user attributes
        /*
        Mode Private allows only this application to use the shared preferences.
         */
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        /*
        Put all the informattion that we got from User object
         */
        editor.putString("address", user.getAddress());
        editor.putString("bio", user.getBio());
        editor.putString("city", user.getCity());
        editor.putString("email", user.getEmail());
        editor.putString("fname", user.getFname());
        editor.putString("gender", user.getGender());
        editor.putString("lname", user.getLname());
        editor.putString("password", user.getPassword());
        editor.putString("phone", user.getPhone());
        editor.putBoolean("is_admin", user.isIs_admin());
        editor.putString("state", user.getState());
        editor.putString("uname", user.getUname());
        editor.putString("zipcode", user.getZipcode());
        editor.putBoolean("is_email_verified", user.isIs_email_verified());
        editor.putBoolean("profile_pic", user.isProfile_pic());
        editor.putInt("user_id", user.getUser_id());
        editor.apply();

    }

    /*
    If user information is already saved in the SharedPreferences then we can
    assume the user is already logged in
     */
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1) != -1;
    }


    /**
     * Save the user's health score.
     * @param score Score to save
     */
    public void saveScore(int score)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("healthScore", score);
        editor.apply();
    }


    /**
     * Returns the user's health score.
     * @return Score or -1 if read failed
     */
    public int getScore()
    {
        SharedPreferences pref = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return pref.getInt("healthScore", -1);
    }

    /**
     * Get the user
     */
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                /*
                Read the values from SharedPreferences object
                 */
                sharedPreferences.getString("address", null),
                sharedPreferences.getString("bio", null),
                sharedPreferences.getString("city", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("fname", null),
                sharedPreferences.getString("gender", null),
                sharedPreferences.getString("lname", null),
                sharedPreferences.getString("password", null),
                sharedPreferences.getString("phone", null),
                sharedPreferences.getString("state", null),
                sharedPreferences.getString("uname", null),
                sharedPreferences.getString("zipcode", null),
                sharedPreferences.getBoolean("is_admin",false),
                sharedPreferences.getBoolean("is_email_verified", true),
                sharedPreferences.getBoolean("profile_pic", true),
                sharedPreferences.getInt("user_id", -1)

        );
    }

    /*
    Method to save the response from the server after successfull login
    Check documentation with the heading POSTMAN RESPONSE to view the response from
    the server.
     */
    public void saveLoginResponse (LoginResponse loginResponse) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("auth_token", loginResponse.getAuth_token());
        editor.putString("message",loginResponse.getMessage());
        editor.putString("status", loginResponse.getStatus());
        editor.apply();
    }

    /*
    Method to get the Login Response from the server
     */
    public LoginResponse getLoginResponse() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new LoginResponse(
                sharedPreferences.getString("auth_token", null),
                sharedPreferences.getString("message", null),
                sharedPreferences.getString("status", null)
        );

    }
    /*
    Method to clear everything saved in the editor. We will use this method
    for signout. Used in SignOut.java class
     */
    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}





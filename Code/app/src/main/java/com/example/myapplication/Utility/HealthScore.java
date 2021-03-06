package com.example.myapplication.Utility;
import android.content.Context;
import android.util.Log;

import com.example.myapplication.LoginStuff.RetrofitClient;
import com.example.myapplication.SharedPrefManager;
import com.fitbitsample.FitbitActivity.PrefConstants;
import com.fitbitsample.FitbitSharedPref.AppPreference;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Provides functionality for retrieving the user's health score
 * from the web service.
 */
public class HealthScore
{
    /**
     * Get user's health score.
     * @return Health score
     */
    public static void getScore(Context c)
    {
        // Set up call objects
        Retrofit retro = new Retrofit.Builder()
                .baseUrl(RetrofitClient.BASE_URL) // Web server url
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ScoreAPI api = retro.create(ScoreAPI.class); // Build API interface
        int id = SharedPrefManager.getInstance(c).getUser().getUser_id();
        Call<Score> call = api.getUserScore(id); // Build API call

        // Make call
        call.enqueue(new Callback<Score>(){
            @Override
            public void onResponse(Call<Score> call, Response<Score> response) {
                SharedPrefManager.getInstance(c).saveScore(response.body().getScore());
            }

            @Override
            public void onFailure(Call<Score> call, Throwable t) {
                SharedPrefManager.getInstance(c).saveScore(0);
            }
        });
    }
}

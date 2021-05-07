package com.example.myapplication.Utility;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ScoreAPI
{
    @GET("score/{user}")
    Call<Score> getUserScore(@Path("user") int user);
}

package com.example.myapplication.Utility;
import com.google.gson.annotations.SerializedName;

public class Score
{
    @SerializedName("score")
    public int score;

    public int getScore()
    {
        return this.score;
    }
}

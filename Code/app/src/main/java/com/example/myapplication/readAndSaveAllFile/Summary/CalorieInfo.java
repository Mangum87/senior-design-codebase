package com.example.myapplication.readAndSaveAllFile.Summary;


/**
 * Holds the calorie data for the summary.
 */
public class CalorieInfo
{
    private double calories;
    private int min;
    private int max;
    private int time;


    /**
     * Sets the values for a range of calorie burn.
     * Any value less than zero will be set to zero.
     * @param calorie Amount of calorie burn
     * @param min Min in range
     * @param max Max in range
     * @param time Total time in range
     */
    public CalorieInfo(double calorie, int min, int max, int time)
    {
        setCalories(calorie);
        setMin(min);
        setMax(max);
        setTime(time);
    }


    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories)
    {
        if(calories < 0)
            this.calories = 0;
        else
            this.calories = calories;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min)
    {
        if(min < 0)
            this.min = 0;
        else
            this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max)
    {
        if(max < 0)
            this.max = 0;
        else
            this.max = max;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time)
    {
        if(time < 0)
            this.time = 0;
        else
            this.time = time;
    }
}

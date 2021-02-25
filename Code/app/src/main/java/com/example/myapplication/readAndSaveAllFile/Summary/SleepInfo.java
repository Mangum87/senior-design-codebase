package com.example.myapplication.readAndSaveAllFile.Summary;


/**
 * Holds the count, minutes, and 30 day average
 * for any sleep state.
 */
public class SleepInfo
{
    private int count;
    private int minutes;
    private int average;


    /**
     * Holds the data to describe the sleep summary.
     * Any values less than zero will be set to zero.
     * @param count Count of states
     * @param minute Total length
     * @param avg 30 day average
     */
    public SleepInfo(int count, int minute, int avg)
    {
        setCount(count);
        setMinutes(minute);
        setAverage(avg);
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count)
    {
        if(count < 0)
            this.count = 0;
        else
            this.count = count;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes)
    {
        if(minutes < 0)
            this.minutes = 0;
        else
            this.minutes = minutes;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average)
    {
        if(average < 0)
            this.average = 0;
        else
            this.average = average;
    }
}

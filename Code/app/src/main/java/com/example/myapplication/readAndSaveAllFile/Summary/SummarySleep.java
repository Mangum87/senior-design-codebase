package com.example.myapplication.readAndSaveAllFile.Summary;


/**
 * Contains summary sleep data for one day.
 */
public class SummarySleep
{
    private int efficiency;
    private int totalSleep;
    private SleepInfo deep;
    private SleepInfo light;
    private SleepInfo rem;
    private SleepInfo wake;
    private String startTime;


    /**
     * Takes a token array from summary file read.
     * @param token Tokens from summary file
     */
    public SummarySleep(String[] token)
    {
        this.efficiency = Integer.parseInt(token[7]);
        this.totalSleep = Integer.parseInt(token[8]);
        this.startTime = token[44];

        setDeep(token);
        setLight(token);
        setRem(token);
        setWake(token);
    }


    public SleepInfo getDeep() {
        return deep;
    }

    private void setDeep(String[] token)
    {
        int c = Integer.parseInt(token[30]);
        int m = Integer.parseInt(token[31]);
        int a = Integer.parseInt(token[32]);

        this.deep = new SleepInfo(c, m, a);
    }

    public SleepInfo getLight() {
        return light;
    }

    private void setLight(String[] token)
    {
        int c = Integer.parseInt(token[33]);
        int m = Integer.parseInt(token[34]);
        int a = Integer.parseInt(token[35]);

        this.light = new SleepInfo(c, m, a);
    }

    public SleepInfo getRem() {
        return rem;
    }

    private void setRem(String[] token)
    {
        int c = Integer.parseInt(token[36]);
        int m = Integer.parseInt(token[37]);
        int a = Integer.parseInt(token[38]);

        this.rem = new SleepInfo(c, m, a);
    }

    public SleepInfo getWake() {
        return wake;
    }

    private void setWake(String[] token)
    {
        int c = Integer.parseInt(token[39]);
        int m = Integer.parseInt(token[40]);
        int a = Integer.parseInt(token[41]);

        this.wake = new SleepInfo(c, m, a);
    }

    public int getEfficiency() {
        return efficiency;
    }

    public int getTotalSleep() {
        return totalSleep;
    }

    public String getStartTime() {
        return startTime;
    }
}

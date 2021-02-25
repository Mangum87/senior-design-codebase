package com.example.myapplication.readAndSaveAllFile.Summary;


/**
 * Contains the summary data for a single day.
 */
public class SummaryFile
{
    private String filename;
    private SummarySleep sleep;
    private SummaryActivity activity;


    public SummaryFile(String filename)
    {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public SummarySleep getSleep()
    {
        return sleep;
    }

    public void setSleep(SummarySleep sleep)
    {
        this.sleep = sleep;
    }

    public SummaryActivity getActivity()
    {
        return activity;
    }

    public void setActivity(SummaryActivity activity)
    {
        this.activity = activity;
    }
}

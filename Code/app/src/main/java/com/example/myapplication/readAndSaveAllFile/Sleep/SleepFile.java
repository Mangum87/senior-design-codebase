package com.example.myapplication.readAndSaveAllFile.Sleep;

import java.util.ArrayList;


/**
 * Retrieve all of the sleep data stored locally
 * for a single day.
 */
public class SleepFile
{
    private final String filename;
    private ArrayList<SleepEvent> events;


    /**
     * Create a record of one night of sleep events.
     * @param filename Name of file
     */
    public SleepFile(String filename)
    {
        this.filename = filename;
        this.events = new ArrayList<SleepEvent>(30);
    }


    /**
     * Add sleep event details.
     * @param state State of sleep
     * @param length Length of event
     * @param time Time event began
     */
    public void addEvent(String state, int length, String time)
    {
        SleepEvent e = new SleepEvent(state, length, time);
        this.events.add(e);
    }


    /**
     * Returns the list of sleep events in file.
     * @return All events
     */
    public ArrayList<SleepEvent> getEvents()
    {
        return this.events;
    }


    /**
     * Returns file name the events are read from.
     * @return File name
     */
    public String getFilename()
    {
        return this.filename;
    }


    /**
     * Returns the date portion of the file name.
     * @return Date of file
     */
    public String getDate()
    {
        return this.filename.substring(5, 15);
    }
}

package com.example.myapplication.readAndSaveAllFile.Sleep;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Stream;

import static android.content.ContentValues.TAG;


/**
 * Retrieve all of the sleep data stored locally
 * for a single day.
 */
public class SleepFile{
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

    /**
     * Returns the total sleep in seconds
     * @return total sleep time
     */
    public int getTotalSeconds(){
        int total = 0;
        for(SleepEvent list : events){
            total += list.getSeconds();
        }
        return total;
    }

    /**
     * @return sleep time in Hour
     */
    public int getTotalHoursSlept(){
        int total = 0;

        for(SleepEvent list : events){
            total += list.getSeconds();
        }

        int hour = total / 60;
        int min = hour % 60;
        hour = hour / 60;

        return hour;
    }

    /**
     * @return sleep time in Minute
     */
    public int getTotalMinuteSlept(){
        int total = 0;

        for(SleepEvent list : events){
            total += list.getSeconds();
        }

        int hour = total / 60;
        int min = hour % 60;
        hour = total / 60;

        return min;
    }

    /**
     * Returns the total sleep state 'Wake' in seconds
     * @return total Wake
     */
    public int getTotalWake(){
        int total = 0;
        for(SleepEvent list : events){
            if(list.getState().toString().equals("WAKE")){
                total += list.getSeconds();
            }
        }
        return total;
    }

    /**
     * Returns the total sleep state 'Light' in seconds
     * @return total Light
     */
    public int getTotalLight(){
        int total = 0;
        for(SleepEvent list : events){
            if(list.getState().toString().equals("LIGHT")){
                total += list.getSeconds();
            }
        }
        return total;
    }

    /**
     * Returns the total sleep state 'Deep' in seconds
     * @return total Deep
     */
    public int getTotalDeep(){
        int total = 0;
        for(SleepEvent list : events){
            if(list.getState().toString().equals("DEEP")){
                total += list.getSeconds();
            }
        }
        return total;
    }

    /**
     * Returns the total sleep state 'Rem' in seconds
     * @return total Rem
     */
    public int getTotalRem(){
        int total = 0;
        for(SleepEvent list : events){
            if(list.getState().toString().equals("REM")){
                total += list.getSeconds();
            }
        }
        return total;
    }
}

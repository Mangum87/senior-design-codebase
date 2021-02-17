package com.example.myapplication.readAndSaveAllFile.Sleep;


/**
 * Records the details of a sleep state
 * change event.
 */
public class SleepEvent
{
    public static enum States {WAKE, LIGHT, DEEP, REM, INVALID}
    private States state;
    private int seconds;
    private String time;


    /**
     * Saves the details of a sleep change event.
     * @param state State of sleep
     * @param sec Length of event
     * @param time Time event began
     */
    public SleepEvent(String state, int sec, String time)
    {
        setState(state);
        setSeconds(sec);
        setTime(time);
    }


    /**
     * Returns the state of sleep event.
     * @return WAKE, LIGHT, DEEP, REM, or INVALID
     */
    public States getState() {
        return state;
    }


    private void setState(String state)
    {
        if(state != null)
            this.state = toState(state.toLowerCase());
        else
            this.state = toState("invalid");
    }


    /**
     * Returns the length of event in seconds.
     * @return Length of event.
     */
    public int getSeconds() {
        return seconds;
    }


    private void setSeconds(int seconds)
    {
        if(seconds >= 0)
            this.seconds = seconds;
        else
            this.seconds = 0;
    }


    /**
     * Returns the time the event started.
     * @return Time of event
     */
    public String getTime() {
        return time;
    }


    private void setTime(String time)
    {
        if(time != null)
            this.time = time;
        else
            this.time = "INVALID";
    }


    /**
     * Translates given string to a States
     * enum value. Returns INVALID if input
     * does not match legitimate state.
     * Input needs to be lower case.
     * @param s String to translate
     * @return States enum value
     */
    private States toState(String s)
    {
        if(s.equals("wake"))
            return States.WAKE;
        else if(s.equals("light"))
            return States.LIGHT;
        else if(s.equals("rem"))
            return States.REM;
        else if(s.equals("deep"))
            return States.DEEP;
        else
            return States.INVALID;
    }
}

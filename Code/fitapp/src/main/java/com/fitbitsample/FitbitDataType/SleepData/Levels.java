package com.fitbitsample.FitbitDataType.SleepData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Levels
{
    @SerializedName("summary")
    @Expose
    private LevelSummary summary;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;
    /*@SerializedName("shortData")
    @Expose
    private List<ShortData> shortData = null;*/

    public LevelSummary getSummary() {
        return summary;
    }

    public void setSummary(LevelSummary summary) {
        this.summary = summary;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    /*public List<ShortData> getShortData() {
        return shortData;
    }

    public void setShortData(List<ShortData> shortData) {
        this.shortData = shortData;
    }*/

    @Override
    public String toString()
    {
        StringBuilder build = new StringBuilder();
        build.append("summary: {\n" + summary.toString() + "\n},\n");

        // Data elements
        build.append("data: [\n");
        if(data != null)
        {
           for(Data d : data) // Build cell for each item in list
           {
               build.append("{\n");
               build.append(d.toString());
               build.append("\n},");
           }
        }
        build.append("\n],\n");

        // Short data elements
        /*build.append("shortData: [\n");
        if(shortData != null) // Build cell for each item in list
        {
            for(ShortData d : shortData) // Build cell for each item in list
            {
                build.append("{\n");
                build.append(d.toString());
                build.append("\n},");
            }
        }
        build.append("\n]");*/

        return build.toString();
    }
}

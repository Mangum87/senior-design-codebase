package com.fitbitsample.FitbitDataType.Hourly;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Intraday
{
    @SerializedName("dataset")
    @Expose
    private List<HourlyDataset> dataset = null;

    @SerializedName("datasetInterval")
    @Expose
    private Integer datasetInterval;

    @SerializedName("datasetType")
    @Expose
    private String datasetType;

    public List<HourlyDataset> getDataset() {
        return dataset;
    }

    public void setDataset(List<HourlyDataset> dataset) {
        this.dataset = dataset;
    }

    public Integer getDatasetInterval() {
        return datasetInterval;
    }

    public void setDatasetInterval(Integer datasetInterval) {
        this.datasetInterval = datasetInterval;
    }

    public String getDatasetType() {
        return datasetType;
    }

    public void setDatasetType(String datasetType) {
        this.datasetType = datasetType;
    }
}

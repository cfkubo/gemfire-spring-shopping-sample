package com.example.model;

public class DataItemResponse {
    private DataItem dataItem;
    private long durationMillis;
    private String source; // "cache" or "database"

    public DataItemResponse(DataItem dataItem, long durationMillis, String source) {
        this.dataItem = dataItem;
        this.durationMillis = durationMillis;
        this.source = source;
    }

    public DataItem getDataItem() {
        return dataItem;
    }

    public long getDurationMillis() {
        return durationMillis;
    }

    public String getSource() {
        return source;
    }
}
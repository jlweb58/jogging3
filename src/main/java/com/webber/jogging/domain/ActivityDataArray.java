package com.webber.jogging.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ActivityDataArray {
    @JsonProperty("type")
    private String type;

    @JsonProperty("data")
    private List<?> data;  // Using wildcard since data type varies

    @JsonProperty("series_type")
    private String seriesType;

    @JsonProperty("original_size")
    private int originalSize;

    @JsonProperty("resolution")
    private String resolution;

    // Getters and setters
    public String getType() { return type; }
    public List<?> getData() { return data; }
    public String getSeriesType() { return seriesType; }
    public int getOriginalSize() { return originalSize; }
    public String getResolution() { return resolution; }
}

package com.webber.jogging.strava.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * This class is internal to the StravaActivityJsonParsingService, enabling easy mapping
 * of the JSON returned from the Strava endpoint {{strava_base_url}}/activities/12698372237/streams?keys=latlng,time,altitude,heartrate
 * to a Java type for further processing
 */
class ActivityDataArray {
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

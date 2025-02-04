package com.webber.jogging.strava.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * This class is used by StravaActivityJsonParsingService, enabling easy mapping
 * of the JSON returned from the Strava endpoint {{strava_base_url}}/activities/12698372237/streams?keys=latlng,time,altitude,heartrate
 * to a Java type for further processing
 */
public record ActivityDataArray (
    @JsonProperty("type")
    String type,
    @JsonProperty("data")
    List<?> data,  // Using wildcard since data type varies
    @JsonProperty("series_type")
    String seriesType,
    @JsonProperty("original_size")
    int originalSize,
    @JsonProperty("resolution")
    String resolution
)
{}

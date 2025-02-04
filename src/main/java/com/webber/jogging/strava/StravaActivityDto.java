package com.webber.jogging.strava;

import com.fasterxml.jackson.annotation.JsonProperty;


public record StravaActivityDto(
        Long id,
        String name,
        String type,
        Double distance,
        @JsonProperty("average_heartrate")
        Double averageHeartRate,
        @JsonProperty("start_date_local")
        String startDateLocal,
        @JsonProperty("moving_time")
        Long movingTime,
        @JsonProperty("map")
        Map map
) { }


package com.webber.jogging.strava;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StravaActivityDto(
        Long id,
        String name,
        String type,
        Double distance,
        @JsonProperty("average_heartrate")
        Double averageHeartDate,
        @JsonProperty("start_date_local")
        String startDateLocal
) { }

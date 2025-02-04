package com.webber.jogging.strava;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Map(
        String id,
        String polyline,
        @JsonProperty("summary_polyline")
        String summaryPolyline
) {}

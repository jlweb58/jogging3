package com.webber.jogging.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record StravaWebhookEvent (@JsonProperty("aspect_type") String aspectType,
    @JsonProperty("event_time") Long eventTime,
    @JsonProperty("object_type") String objectType,
    @JsonProperty("object_id") Long objectId,
    @JsonProperty("owner_id") Long ownerId,
    @JsonProperty("updates") Map<String, Object> updates
) {}


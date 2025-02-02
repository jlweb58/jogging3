package com.webber.jogging.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record StravaWebhookEvent (@JsonProperty("aspect_type") String aspectType,
    @JsonProperty("event_time") Long eventTime,
    @JsonProperty("object_type") String objectType,
    @JsonProperty("object_id") Long objectId,
    @JsonProperty("owner_id") Long ownerId,
    @JsonProperty("updates") List<String> updates
) {}


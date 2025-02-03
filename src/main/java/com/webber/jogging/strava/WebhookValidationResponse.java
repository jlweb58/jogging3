package com.webber.jogging.strava;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WebhookValidationResponse(@JsonProperty("hub.challenge") String challenge) {
}

package com.webber.jogging.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WebhookValidationResponse(@JsonProperty("hub.challenge") String challenge) {
}

package com.webber.jogging.controller;

import java.util.List;

record StravaWebhookEvent (
     String aspectType,
    String objectType,
    Long objectId,
    Long ownerId,
    List<String> updates,
    String authorization
) {}


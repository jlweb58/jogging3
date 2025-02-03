package com.webber.jogging.domain;

import java.time.Instant;

public record GpxTrackElement(double latitude, double longitude, double elevation, int heartRate, Instant timestamp) { }

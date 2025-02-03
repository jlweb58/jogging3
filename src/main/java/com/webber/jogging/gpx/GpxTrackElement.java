package com.webber.jogging.gpx;

import java.time.Instant;

public record GpxTrackElement(double latitude, double longitude, double elevation, int heartRate, Instant timestamp) { }

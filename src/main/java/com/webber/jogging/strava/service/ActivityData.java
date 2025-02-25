package com.webber.jogging.strava.service;

import java.time.Instant;
import java.util.List;

/**
 * This class is internal to the StravaActivityJsonParsingService, representing the complete
 * data from a Strava activity as extracted from the Strava streaming API, allowing further processing
 * to a ParsedGpxTrack
 */
class ActivityData {

    private List<double[]> coordinates;

    private List<Double> distances;

    private List<Double> altitudes;

    private List<Integer> heartRates;

    private List<Integer> elapsedSeconds;

    private Instant startTime;

    public List<Integer> getElapsedSeconds() {
        return elapsedSeconds;
    }

    public void setElapsedSeconds(List<Integer> elapsedSeconds) {
        this.elapsedSeconds = elapsedSeconds;
    }

    public List<Integer> getHeartRates() {
        return heartRates;
    }

    public void setHeartRates(List<Integer> heartRates) {
        this.heartRates = heartRates;
    }

    public List<Double> getAltitudes() {
        return altitudes;
    }

    public void setAltitudes(List<Double> altitudes) {
        this.altitudes = altitudes;
    }

    public List<Double> getDistances() {
        return distances;
    }

    public void setDistances(List<Double> distances) {
        this.distances = distances;
    }

    public List<double[]> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<double[]> coordinates) {
        this.coordinates = coordinates;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }


}

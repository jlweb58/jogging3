package com.webber.jogging.service;

import com.webber.jogging.domain.ParsedGpxTrack;
import com.webber.jogging.domain.GpxTrackElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GpxTrackParserTest {

    private GpxTrackParser gpxTrackParser;

    @BeforeEach
    public void setUp() throws Exception {
        gpxTrackParser = new GpxTrackParser();
    }

    @Test
    public void testCreatesGpxTrackElement() throws Exception {
        ParsedGpxTrack gpxTrack = gpxTrackParser.parseGpxTrack(this.getClass().getResourceAsStream("/TestGpxTrack.gpx"));
        assertNotNull(gpxTrack);
        assertNotNull(gpxTrack.getTrackElements());
        assertEquals(4, gpxTrack.getTrackElements().size());
        assertTrackElementCorrect(gpxTrack.getTrackElements().get(0),11.6707190, 48.1012620, 540.7, 68,  Instant.parse("2020-11-01T08:30:57Z"));
        assertTrackElementCorrect(gpxTrack.getTrackElements().get(1),11.6706970, 48.1012610, 540.7, 68,  Instant.parse("2020-11-01T08:30:58Z"));
        assertTrackElementCorrect(gpxTrack.getTrackElements().get(2),11.6706700, 48.1012630, 540.7, 72,  Instant.parse("2020-11-01T08:30:59Z"));
        assertTrackElementCorrect(gpxTrack.getTrackElements().get(3),11.6705300, 48.1012880, 540.6, 75,  Instant.parse("2020-11-01T08:31:03Z"));
    }

    private void assertTrackElementCorrect(GpxTrackElement trackElement, double expectedLongitude, double expectedLatitude, double expectedElevation, int expectedHeartrate, Instant expectedTimestamp) {
        assertEquals(expectedLongitude, trackElement.longitude());
        assertEquals(expectedLatitude, trackElement.latitude());
        assertEquals(expectedElevation, trackElement.elevation());
        assertEquals(expectedHeartrate, trackElement.heartRate());
        assertEquals(expectedTimestamp, trackElement.timestamp());
    }
}

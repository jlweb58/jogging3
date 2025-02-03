package com.webber.jogging.strava.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.webber.jogging.Application;
import com.webber.jogging.gpx.GpxTrackElement;
import com.webber.jogging.gpx.ParsedGpxTrack;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
@Transactional
public class StravaActivityJsonParsingServiceIntTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void itShouldReadAFileAndParseActivityData() throws Exception {
        // Given
        ClassPathResource resource = new ClassPathResource("activityStream.json");
        String jsonContent = new String(resource.getInputStream().readAllBytes());
        Instant startTime = Instant.parse("2024-02-01T10:00:00Z"); // Use a fixed time for testing

        StravaActivityJsonParsingService service = new StravaActivityJsonParsingService(new ObjectMapper());

        // When
        ParsedGpxTrack gpxTrack = service.parseActivityJson(jsonContent, startTime);

        // Then
        assertNotNull(gpxTrack);
        List<GpxTrackElement> elements = gpxTrack.getTrackElements();
        assertEquals(432, elements.size());

        // Verify first track element
        GpxTrackElement firstElement = elements.get(0);
        assertEquals(48.101306, firstElement.latitude(), 0.000001);
        assertEquals(11.670689, firstElement.longitude(), 0.000001);
        assertEquals(541.4, firstElement.elevation(), 0.1);
        assertEquals(74, firstElement.heartRate());
        assertEquals(startTime, firstElement.timestamp());

        // Verify last track element
        GpxTrackElement lastElement = elements.get(elements.size() - 1);
        assertEquals(48.101227, lastElement.latitude(), 0.000001);
        assertEquals(11.670769, lastElement.longitude(), 0.000001);
        assertEquals(541.4, lastElement.elevation(), 0.1);
        assertEquals(159, lastElement.heartRate());
        assertEquals(startTime.plusSeconds(3023), lastElement.timestamp());
    }
}

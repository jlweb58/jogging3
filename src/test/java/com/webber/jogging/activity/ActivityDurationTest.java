package com.webber.jogging.activity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActivityDurationTest {




    @Test
    public void itShouldCreateTheCorrectActivityDuration() {
        ActivityDuration duration1 = ActivityDuration.fromSeconds(60);
        assertEquals("00:01:00", duration1.toString());
        duration1 = ActivityDuration.fromSeconds(61);
        assertEquals("00:01:01", duration1.toString());
        duration1 = ActivityDuration.fromSeconds(3600);
        assertEquals("01:00:00", duration1.toString());
        duration1 = ActivityDuration.fromSeconds(3661);
        assertEquals("01:01:01", duration1.toString());
        duration1 = ActivityDuration.fromSeconds(3660);
        assertEquals("01:01:00", duration1.toString());
    }
}

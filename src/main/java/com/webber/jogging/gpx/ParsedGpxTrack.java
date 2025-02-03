package com.webber.jogging.gpx;

import java.util.Collections;
import java.util.List;

public class ParsedGpxTrack {

    private List<GpxTrackElement> trackElements;

    public List<GpxTrackElement> getTrackElements() {
        return Collections.unmodifiableList(trackElements);
    }

    public void setTrackElements(List<GpxTrackElement> trackElements) {
        this.trackElements = trackElements;
    }
}

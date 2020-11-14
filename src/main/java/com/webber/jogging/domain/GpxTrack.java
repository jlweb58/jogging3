package com.webber.jogging.domain;

import java.util.Collections;
import java.util.List;

public class GpxTrack {

    private List<GpxTrackElement> trackElements;

    public List<GpxTrackElement> getTrackElements() {
        return Collections.unmodifiableList(trackElements);
    }

    public void setTrackElements(List<GpxTrackElement> trackElements) {
        this.trackElements = trackElements;
    }
}

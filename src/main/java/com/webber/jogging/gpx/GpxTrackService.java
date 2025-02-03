package com.webber.jogging.gpx;

import java.util.Optional;

public interface GpxTrackService {

    ParsedGpxTrack save(GpxTrack gpxTrack) throws Exception;

    ParsedGpxTrack findForId(Long activityId) throws Exception;

    Optional<GpxTrack> findUnparsedForId(Long id) throws Exception;
}

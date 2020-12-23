package com.webber.jogging.service;

import com.webber.jogging.domain.GpxTrack;
import com.webber.jogging.domain.ParsedGpxTrack;

import java.util.Optional;

public interface GpxTrackService {

    ParsedGpxTrack save(GpxTrack gpxTrack) throws Exception;

    ParsedGpxTrack findForId(Long runId) throws Exception;

    Optional<GpxTrack> findUnparsedForId(Long id) throws Exception;
}

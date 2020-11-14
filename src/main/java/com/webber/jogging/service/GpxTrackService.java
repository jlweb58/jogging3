package com.webber.jogging.service;

import com.webber.jogging.domain.GpxTrack;
import com.webber.jogging.domain.ParsedGpxTrack;

public interface GpxTrackService {

    ParsedGpxTrack create(GpxTrack gpxTrack) throws Exception;

    ParsedGpxTrack findForId(Long runId) throws Exception;
}

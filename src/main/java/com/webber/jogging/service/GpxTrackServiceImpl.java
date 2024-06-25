package com.webber.jogging.service;

import com.webber.jogging.domain.GpxTrack;
import com.webber.jogging.domain.ParsedGpxTrack;
import com.webber.jogging.repository.GpxTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Optional;

@Service
public class GpxTrackServiceImpl implements GpxTrackService {

    private final GpxTrackRepository repository;

    @Autowired
    public GpxTrackServiceImpl(GpxTrackRepository repository) {
        this.repository = repository;
    }

    @Override
    public ParsedGpxTrack save(GpxTrack gpxTrack) throws Exception {
        GpxTrack created = repository.save(gpxTrack);
        return new GpxTrackParser().parseGpxTrack(new ByteArrayInputStream(gpxTrack.getGpxTrack().getBytes()));
    }

    @Override
    public ParsedGpxTrack findForId(Long activityId) throws Exception {
        GpxTrack gpxTrack = repository.getOne(activityId);
        return new GpxTrackParser().parseGpxTrack(new ByteArrayInputStream(gpxTrack.getGpxTrack().getBytes()));
    }

    @Override
    public Optional<GpxTrack> findUnparsedForId(Long id)  {
            return repository.findById(id);
    }
}

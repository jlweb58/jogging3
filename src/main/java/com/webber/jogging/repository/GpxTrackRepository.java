package com.webber.jogging.repository;

import com.webber.jogging.domain.GpxTrack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GpxTrackRepository extends JpaRepository<GpxTrack, Long> {

}

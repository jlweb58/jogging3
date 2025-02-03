package com.webber.jogging.strava.service;

import com.webber.jogging.strava.StravaActivityDto;
import reactor.core.publisher.Mono;

public interface StravaActivityService {

    Mono<StravaActivityDto> getActivity(Long activityId);
}

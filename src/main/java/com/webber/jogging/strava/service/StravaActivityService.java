package com.webber.jogging.strava.service;

import com.webber.jogging.strava.StravaActivityDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface StravaActivityService {

    Mono<StravaActivityDto> getActivity(Long activityId);

    Mono<List<ActivityDataArray>> getActivityStreams(long activityId);
}

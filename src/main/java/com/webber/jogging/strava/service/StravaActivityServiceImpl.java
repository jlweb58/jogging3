package com.webber.jogging.strava.service;

import com.webber.jogging.activity.ActivityService;
import com.webber.jogging.strava.StravaActivityDto;
import com.webber.jogging.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class StravaActivityServiceImpl implements StravaActivityService {

    private static final String STRAVA_API_BASE_URL = "https://www.strava.com/api/v3";

    private final StravaAuthenticationService stravaAuthenticationService;

    private final WebClient webClient;

    public StravaActivityServiceImpl(StravaAuthenticationService stravaAuthenticationService, UserService userService, ActivityService activityService, WebClient.Builder webClientBuilder) {
        this.stravaAuthenticationService = stravaAuthenticationService;
        this.webClient = webClientBuilder.baseUrl(STRAVA_API_BASE_URL).build();
    }

    @Override
    public Mono<StravaActivityDto> getActivity(Long activityId) {
        return webClient.get()
                .uri("/activities/{activityId}", activityId)
                .headers(headers -> headers.setBearerAuth(stravaAuthenticationService.getDefaultToken().getAccessToken()))
                .retrieve()
                .bodyToMono(StravaActivityDto.class)
                .doOnSuccess(activity -> log.info("Fetched activity: {} ({}) - {} meters, started at {}",
                        activity.name(),
                        activity.type(),
                        activity.distance(),
                        activity.startDateLocal()))
                .doOnError(throwable -> log.error("Error fetching activity: {}: {}", activityId, throwable.getMessage()))
                ;
    }

}

package com.webber.jogging.strava.service;

import com.webber.jogging.strava.StravaActivityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StravaActivityServiceImpl implements StravaActivityService {

    private static final String STRAVA_API_BASE_URL = "https://www.strava.com/api/v3";

    private static final Logger LOGGER = LoggerFactory.getLogger(StravaActivityServiceImpl.class);

    private final StravaAuthenticationService stravaAuthenticationService;

    private final WebClient webClient;

    public StravaActivityServiceImpl(StravaAuthenticationService stravaAuthenticationService, WebClient.Builder webClientBuilder) {
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
                .doOnSuccess(activity -> LOGGER.info("Fetched activity: {} ({}) - {} meters, started at {}",
                        activity.name(),
                        activity.type(),
                        activity.distance(),
                        activity.startDateLocal()))
                .doOnError(throwable -> LOGGER.error("Error fetching activity: {}: {}", activityId, throwable.getMessage()))
                ;
    }
}

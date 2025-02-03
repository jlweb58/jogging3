package com.webber.jogging.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class StravaActivityServiceImpl implements StravaActivityService {

    private static final String STRAVA_API_BASE_URL = "https://www.strava.com/api/v3";

    private final StravaAuthenticationService stravaAuthenticationService;

    private final WebClient webClient;

    public StravaActivityServiceImpl(StravaAuthenticationService stravaAuthenticationService, WebClient.Builder webClientBuilder) {
        this.stravaAuthenticationService = stravaAuthenticationService;
        this.webClient = webClientBuilder.baseUrl(STRAVA_API_BASE_URL).build();
    }



}

package com.webber.jogging.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webber.jogging.domain.StravaAuthentication;
import com.webber.jogging.domain.User;
import com.webber.jogging.repository.StravaAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class StravaAuthenticationServiceImpl implements StravaAuthenticationService {

    private final StravaAuthenticationRepository repository;

    private final WebClient webClient;

    private final String stravaOathUrl = "https://www.strava.com";

    private final String clientId = "48109";

    private final String clientSecret = "9200ffa2668e175835fd7b42b31f7c5def2eec2b";

    private String code = "5b12f129e71ce8843e52293046d37c5c195b1d36";

    private static final String GRANT_TYPE = "authorization_code";


    @Autowired
    public StravaAuthenticationServiceImpl(StravaAuthenticationRepository repository, WebClient.Builder webClientBuilder) {
        this.repository = repository;
        this.webClient = webClientBuilder.baseUrl(stravaOathUrl).build();
    }


    @Override
    public StravaAuthentication obtainAccessToken(User user) {
        return webClient.post().uri(builder -> builder.path("/oauth/token")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", code)
                .queryParam("grant_type", GRANT_TYPE)
                .build()
        ).retrieve().bodyToMono(String.class)
                .map(jsonString -> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        JsonNode jsonNode = mapper.readTree(jsonString);
                        String accessToken = jsonNode.get("access_token").asText();
                        String refreshToken = jsonNode.get("refresh_token").asText();
                        long expiresAt = jsonNode.get("expires_at").asLong() * 1000;
                        Date expirationDate = new Date(expiresAt);
                        return new StravaAuthentication(accessToken, refreshToken, user, expirationDate);
                    } catch (Exception e) {
                        return null;
                    }
                } ).block();
    }


    @Override
    public void refreshAccessToken() {


    }

    @Override
    public StravaAuthentication findByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public StravaAuthentication create(StravaAuthentication authentication) {
        return repository.save(authentication);
    }
}

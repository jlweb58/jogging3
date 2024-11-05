package com.webber.jogging.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webber.jogging.domain.StravaAuthentication;
import com.webber.jogging.domain.User;
import com.webber.jogging.repository.StravaAuthenticationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class StravaAuthenticationServiceImpl implements StravaAuthenticationService {

    private final StravaAuthenticationRepository repository;

    private final WebClient webClient;

    private final String stravaOathUrl = "https://www.strava.com";

    private final String clientId = "48109";

    private final String clientSecret = "9200ffa2668e175835fd7b42b31f7c5def2eec2b";

    private String code = "5b12f129e71ce8843e52293046d37c5c195b1d36";

    private static final String GRANT_TYPE_AUTHORIZATION = "authorization_code";

    private static final String GRANT_TYPE_REFRESH = "refresh_token";

    private static final String DEFAULT_USER = "jwebber";

    private static final Logger LOGGER = LoggerFactory.getLogger(StravaAuthenticationServiceImpl.class);

    @Autowired
    private UserService userService;

    //TODO - extract a StravaCommunicationService

    @Autowired
    public StravaAuthenticationServiceImpl(StravaAuthenticationRepository repository, WebClient.Builder webClientBuilder) {
        this.repository = repository;
        this.webClient = webClientBuilder.baseUrl(stravaOathUrl).build();
    }


    // TODO - investigate whether this method is really needed
    @Override
    public StravaAuthentication obtainAccessToken(User user) {
        return webClient.post().uri(builder -> builder.path("/oauth/token")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", code)
                .queryParam("grant_type", GRANT_TYPE_AUTHORIZATION)
                .build()
        ).retrieve().bodyToMono(String.class)
                .map(jsonString -> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        JsonNode jsonNode = mapper.readTree(jsonString);
                        String accessToken = jsonNode.get("access_token").asText();
                        String refreshToken = jsonNode.get("refresh_token").asText();
                        long expiresAt = jsonNode.get("expires_at").asLong();
                        ZoneId zone = ZoneId.of("Europe/Berlin");

                        LocalDateTime expirationDate = LocalDateTime.ofEpochSecond(expiresAt, 0, zone.getRules().getOffset(LocalDateTime.now()));
                        return new StravaAuthentication(accessToken, refreshToken, user, expirationDate);
                    } catch (Exception e) {
                        return null;
                    }
                } ).block();
    }


    @Override
    public void refreshAccessToken() {

        User user = userService.find(DEFAULT_USER);
        StravaAuthentication authentication = findByUser(user);
        LOGGER.info("Found authentication: " + authentication);
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(authentication.getExpirationDate())) {
            LOGGER.info("Access token not expired");
            return;
        }
        // Access token will expire in less than an hour, we need to refresh it
        webClient.post().uri(builder -> builder.path("/oauth/token")
                        .queryParam("client_id", clientId)
                        .queryParam("client_secret", clientSecret)
                        .queryParam("refresh_token", authentication.getRefreshToken())
                        .queryParam("grant_type", GRANT_TYPE_REFRESH)
                        .build()
                ).retrieve().bodyToMono(String.class)
                .map(jsonString -> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        JsonNode jsonNode = mapper.readTree(jsonString);
                        String accessToken = jsonNode.get("access_token").asText();
                        String refreshToken = jsonNode.get("refresh_token").asText();
                        long expiresAt = jsonNode.get("expires_at").asLong();
                        ZoneId zone = ZoneId.of("Europe/Berlin");

                        LocalDateTime expirationDate = LocalDateTime.ofEpochSecond(expiresAt, 0, zone.getRules().getOffset(LocalDateTime.now()));
                        authentication.setAccessToken(accessToken);
                        authentication.setRefreshToken(refreshToken);
                        authentication.setExpirationDate(expirationDate);
                        //TODO - change to create(authentication)
                        return repository.save(authentication);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } ).block();

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

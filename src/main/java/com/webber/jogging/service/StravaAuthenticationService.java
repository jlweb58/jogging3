package com.webber.jogging.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webber.jogging.domain.StravaAuthentication;
import com.webber.jogging.domain.User;
import reactor.core.publisher.Mono;

public interface StravaAuthenticationService {

    StravaAuthentication obtainAccessToken(User user);

    void refreshAccessToken();

    StravaAuthentication findByUser(User user);

    StravaAuthentication create(StravaAuthentication authentication);
}

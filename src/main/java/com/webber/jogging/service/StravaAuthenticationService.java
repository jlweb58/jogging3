package com.webber.jogging.service;

import com.webber.jogging.domain.StravaAuthentication;
import com.webber.jogging.domain.User;

public interface StravaAuthenticationService {

    StravaAuthentication obtainAccessToken(User user);

    void refreshAccessToken();

    StravaAuthentication findByUser(User user);

    StravaAuthentication create(StravaAuthentication authentication);
}

package com.webber.jogging.service;

import com.webber.jogging.domain.StravaAuthentication;
import com.webber.jogging.domain.User;

public interface StravaAuthenticationService {

    StravaAuthentication obtainAccessToken(User user);

    void refreshAccessToken();

    StravaAuthentication findByUser(User user);

    StravaAuthentication create(StravaAuthentication authentication);

    /**
     * Since we only have one user in the system (me), this will currently return the only
     * Strava authentication token we have.
     * @return A valid StravaAuthentication
     */
    StravaAuthentication getDefaultToken();
}

package com.webber.jogging.strava.service;

import com.webber.jogging.domain.User;
import com.webber.jogging.strava.StravaAuthentication;

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

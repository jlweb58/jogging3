package com.webber.jogging.task;

import com.webber.jogging.domain.StravaAuthentication;
import com.webber.jogging.domain.User;
import com.webber.jogging.service.StravaAuthenticationService;
import com.webber.jogging.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class RefreshStravaTokenTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshStravaTokenTask.class);

    private static final long HOUR = 1000  * 60 * 60;

    @Autowired
    private StravaAuthenticationService stravaAuthenticationService;

    @Scheduled(fixedRate = HOUR)
    public void refreshStravaToken() {
        LOGGER.info("Scheduled Strava Task:" + LocalDateTime.now());
        stravaAuthenticationService.refreshAccessToken();;
    }

}

package com.webber.jogging.task;

import com.webber.jogging.strava.service.StravaAuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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

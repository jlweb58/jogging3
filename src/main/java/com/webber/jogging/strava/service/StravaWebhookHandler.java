package com.webber.jogging.strava.service;

import com.webber.jogging.activity.ActivityService;
import com.webber.jogging.strava.StravaWebhookEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class StravaWebhookHandler {

    private final StravaActivityService stravaActivityService;

    private final ActivityService activityService;

    public StravaWebhookHandler(StravaActivityService activityService, ActivityService activityService1) {
        this.stravaActivityService = activityService;
        this.activityService = activityService1;
    }

    @Transactional( propagation = Propagation.REQUIRES_NEW)
    public void handleActivityCreated(StravaWebhookEvent stravaWebhookEvent) {
        if ("activity".equals(stravaWebhookEvent.objectType())) {
            stravaActivityService.getActivity(stravaWebhookEvent.objectId())
                    .subscribe(
                            activityService::processNewActivity,
                            error -> log.error("Failed to fetch activity {}: {}",
                                    stravaWebhookEvent.objectId(), error.getMessage())
                    );
        }

    }
}

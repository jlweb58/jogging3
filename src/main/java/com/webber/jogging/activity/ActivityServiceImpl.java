package com.webber.jogging.activity;

import com.webber.jogging.gpx.GpxTrack;
import com.webber.jogging.gpx.GpxTrackService;
import com.webber.jogging.gpx.ParsedGpxTrack;
import com.webber.jogging.gpx.xml.GpxConverter;
import com.webber.jogging.strava.StravaActivityDto;
import com.webber.jogging.strava.service.StravaActivityService;
import com.webber.jogging.strava.service.StravaActivityStreamJsonParsingService;
import com.webber.jogging.user.User;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    private final EntityManager entityManager;

    private final StravaActivityService stravaActivityService;

    private final StravaActivityStreamJsonParsingService stravaActivityStreamJsonParsingService;

    private final GpxTrackService gpxTrackService;

    private final GpxConverter gpxConverter;

    private final TransactionTemplate transactionTemplate;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository, PlatformTransactionManager transactionManager,
                               EntityManager entityManager, StravaActivityService stravaActivityService,
                               StravaActivityStreamJsonParsingService stravaActivityStreamJsonParsingService,
                               GpxTrackService gpxTrackService, GpxConverter gpxConverter) {
        this.activityRepository = activityRepository;
        this.entityManager = entityManager;
        this.stravaActivityService = stravaActivityService;
        this.stravaActivityStreamJsonParsingService = stravaActivityStreamJsonParsingService;
        this.gpxTrackService = gpxTrackService;
        this.gpxConverter = gpxConverter;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    public Activity create(Activity activity) {
        if (activity.getId() != null) {
            throw new IllegalArgumentException("Activity " + activity + " already exists");
        }
        activity.getUser().addActivity(activity);
        return activityRepository.save(activity);
    }

    @Override
    public Activity save(Activity activity) {
       return activityRepository.save(activity);
    }

    @Override
    public void delete(Activity activity) {
        activityRepository.delete(activity);
    }

    @Override
    public Activity find(long id) {
        Optional<Activity> optional = activityRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<Activity> loadAll(User user) {
        return activityRepository.findAllByUserOrderByDateDesc(user);
    }

    @Override
    public List<Activity> search(ActivityFilter activityFilter) {
        return activityRepository.findAll(ActivitySpecifications.forActivityFilter(activityFilter));
    }

    @Override
    public double getDistanceForDateRange(Date startDate, Date endDate, User user) {
        List<Activity> activities = activityRepository.findAll(ActivitySpecifications.forActivityFilter(new ActivityFilter(null, startDate, endDate, user)));
        return activities.stream().mapToDouble(Activity::getDistance).sum();
    }

    @Override
    public void processNewActivity(StravaActivityDto activityDto) {
        // This looks strange, but I found no other way of making this work inside the
        // transaction boundaries, always got a "could not initialize proxy" error.
        User user = entityManager.createQuery(
                        "SELECT u FROM User u LEFT JOIN FETCH u.activities WHERE u.username = :username",
                        User.class)
                .setParameter("username", "jwebber")
                .getSingleResult();
        ActivityDuration activityDuration = ActivityDuration.fromSeconds(activityDto.movingTime());
        int heartRate = Math.toIntExact(Math.round(activityDto.averageHeartRate()));
        double distance = activityDto.distance() / 1000.0;
        ActivityType activityType = ActivityType.fromStravaTypeString(activityDto.type());
        Date date = Date.from(LocalDateTime.parse(activityDto.startDateLocal(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")).toInstant(ZoneOffset.UTC));
        String course = activityDto.name();
        Activity activity = Activity.build(date, course, distance, activityDuration, null, null, heartRate, user, activityType);
        this.create(activity);

        if (activityDto.map() != null && !StringUtils.isEmpty(activityDto.map().polyline())) {
            processActivityStream(activity.getId(), activityDto.id(), date.toInstant(), user)
                    .subscribe(
                            savedActivity -> log.info("Successfully processed stream for activity: {}", activity.getId()),
                            error -> log.error("Error processing stream for activity: {}: {}", activity.getId(), error.getMessage())
                    );
        }

    }


    public Mono<ParsedGpxTrack> processActivityStream(Long activityId, Long stravaActivityId, Instant activityStartDate, User user) {
            return stravaActivityService.getActivityStreams(stravaActivityId)
                    .flatMap(streamArrays -> {
                        ParsedGpxTrack parsedGpxTrack = stravaActivityStreamJsonParsingService.parseActivityDataStream(streamArrays, activityStartDate);
                        return updateActivityWithGpxTrack(activityId, parsedGpxTrack, user);

                    })
                    .doOnError(error -> log.error("Failed to process activity stream for activity {}: {} ", activityId, error.getMessage()));
    }

    protected Mono<ParsedGpxTrack> updateActivityWithGpxTrack(Long activityId, ParsedGpxTrack parsedGpxTrack, User user)  {
       return Mono.fromCallable(() ->
       transactionTemplate.execute(status -> {
           Activity activity = entityManager.find(Activity.class, activityId);
           activity = entityManager.merge(activity);
           String gpxTrackXmlString = gpxConverter.convertToGpx(parsedGpxTrack, activity.getCourse());
           GpxTrack gpxTrack = new GpxTrack(gpxTrackXmlString, activity, user);
           try {
               return gpxTrackService.save(gpxTrack);

           } catch (Exception e) {
               throw new RuntimeException(e);
           }
       })
       );
    }

}

package com.webber.jogging.activity;

import com.webber.jogging.strava.StravaActivityDto;
import com.webber.jogging.user.User;
import com.webber.jogging.user.UserService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    private final UserService userService;

    private final EntityManager entityManager;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository, UserService userService, EntityManager entityManager) {
        this.activityRepository = activityRepository;
        this.userService = userService;
        this.entityManager = entityManager;
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
        processNewActivityTransactional(activityDto);
    }

    protected void processNewActivityTransactional(StravaActivityDto activityDto) {
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


    }
}

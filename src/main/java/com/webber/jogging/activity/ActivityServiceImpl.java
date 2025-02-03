package com.webber.jogging.activity;

import com.webber.jogging.security.UserNotFoundException;
import com.webber.jogging.strava.StravaActivityDto;
import com.webber.jogging.user.User;
import com.webber.jogging.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    private final UserService userService;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository, UserService userService) {
        this.activityRepository = activityRepository;
        this.userService = userService;
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
        try {
            User user = userService.getCurrentUser();
            ActivityDuration activityDuration = ActivityDuration.fromSeconds(activityDto.movingTime());
            int heartRate = Math.toIntExact(Math.round(activityDto.averageHeartRate()));
            double distance = activityDto.distance() / 1000.0;
            ActivityType activityType = ActivityType.fromStravaTypeString(activityDto.type());
            Date date = Date.from(LocalDateTime.parse(activityDto.startDateLocal()).toInstant(ZoneOffset.UTC));
            String course = activityDto.name();
            Activity activity = Activity.build(date, course, distance, activityDuration, null, null, heartRate, user, activityType);
            this.create(activity);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

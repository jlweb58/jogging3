package com.webber.jogging.activity;

import com.webber.jogging.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
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
}

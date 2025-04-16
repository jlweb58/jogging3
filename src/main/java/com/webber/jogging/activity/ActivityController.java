package com.webber.jogging.activity;

import com.webber.jogging.security.UserNotFoundException;
import com.webber.jogging.user.User;
import com.webber.jogging.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/jogging")
public class ActivityController {

    private final UserService userService;

    private final ActivityService activityService;

    public ActivityController(UserService userService, ActivityService activityService) {
        this.userService = userService;
        this.activityService = activityService;
    }

    @GetMapping(path = "/hello")
    public String sayPlainTextHello() {
        return "Hello";
    }

    @GetMapping(path = "/activities", produces = "application/json")
    public ResponseEntity<List<Activity>> listAll() throws UserNotFoundException {
        User user = userService.getCurrentUser();
        List<Activity> activities = activityService.loadAll(user);
        return ResponseEntity.ok(activities);
    }

    @PostMapping(path="/activities", produces = "application/json")
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) throws UserNotFoundException {
        User user = userService.getCurrentUser();
        activity.setUser(user);
        // If the activity doesn't have a timestamp, set it to current time
        if (activity.getDate() == null) {
            activity.setDate(new Date());
        }
        Activity created = activityService.create(activity);
        return ResponseEntity.ok(created);
    }

    @PutMapping(path="/activities/{id}", produces = "application/json")
    public ResponseEntity<Activity> updateActivity(@RequestBody Activity activity, @PathVariable Long id) throws UserNotFoundException {
        User user = userService.getCurrentUser();
        // Ensure we're working with the existing activity
        Activity existingActivity = activityService.find(id);
        if (existingActivity == null) {
            return ResponseEntity.notFound().build();
        }

        // Check ownership
        if (!existingActivity.getUser().getId().equals(user.getId())) {
            log.error("User {} is attempting to update activity owned by {}",
                    user.getUsername(), existingActivity.getUser().getUsername());
            return ResponseEntity.status(403).build();
        }
        activity.setUser(user);
        // Ensure activity has a timestamp
        if (activity.getDate() == null) {
            activity.setDate(existingActivity.getDate());
        }
        Activity updated = activityService.save(activity);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path="/activities/{activityId}", produces = "application/json")
    public ResponseEntity<String> deleteActivity(@PathVariable Long activityId) throws UserNotFoundException {
        User user = userService.getCurrentUser();
        Activity existingActivity = activityService.find(activityId);
        if (!existingActivity.getUser().getId().equals(user.getId())) {
           log.error("User {} is not the owner of the activity", user.getUsername());
           throw new IllegalArgumentException("User " + user.getUsername() + " is not the owner of the activity");
        }
        activityService.delete(existingActivity);
        return ResponseEntity.ok("Activity deleted");
    }

}

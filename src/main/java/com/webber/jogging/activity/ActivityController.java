package com.webber.jogging.activity;

import com.webber.jogging.security.UserNotFoundException;
import com.webber.jogging.user.User;
import com.webber.jogging.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Activity created = activityService.create(activity);
        return ResponseEntity.ok(created);
    }

    @PutMapping(path="/activities/{id}", produces = "application/json")
    public ResponseEntity<Activity> updateActivity(@RequestBody Activity activity, @PathVariable Long id) throws UserNotFoundException {
        User user = userService.getCurrentUser();
        activity.setUser(user);
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

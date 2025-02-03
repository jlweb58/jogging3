package com.webber.jogging.activity;

import com.webber.jogging.security.UserNotFoundException;
import com.webber.jogging.user.User;
import com.webber.jogging.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jogging")
public class ActivityController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

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

}

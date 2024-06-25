package com.webber.jogging.controller;

import com.webber.jogging.domain.Activity;
import com.webber.jogging.domain.User;
import com.webber.jogging.service.ActivityService;
import com.webber.jogging.service.UserService;
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
public class RunController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @GetMapping(path = "/hello")
    public String sayPlainTextHello() {
        return "Hello";
    }

    @GetMapping(path = "/runs", produces = "application/json")
    public ResponseEntity<List<Activity>> listAll() throws UserNotFoundException {
        User user = userService.getCurrentUser();
        List<Activity> activities = activityService.loadAll(user);
        return ResponseEntity.ok(activities);
    }

    @PostMapping(path="/runs", produces = "application/json")
    public ResponseEntity<Activity> createRun(@RequestBody Activity activity) throws UserNotFoundException {
        User user = userService.getCurrentUser();
        activity.setUser(user);
        Activity created = activityService.create(activity);
        return ResponseEntity.ok(created);
    }

    @PutMapping(path="/runs/{id}", produces = "application/json")
    public ResponseEntity<Activity> updateRun(@RequestBody Activity activity, @PathVariable Long id) throws UserNotFoundException {
        User user = userService.getCurrentUser();
        activity.setUser(user);
        Activity updated = activityService.save(activity);
        return ResponseEntity.ok(updated);
    }

}

package com.webber.jogging.controller;

import com.webber.jogging.domain.Run;
import com.webber.jogging.domain.User;
import com.webber.jogging.service.RunService;
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
    private RunService runService;

    @GetMapping(path = "/hello")
    public String sayPlainTextHello() {
        return "Hello";
    }

    @GetMapping(path = "/runs", produces = "application/json")
    public ResponseEntity<List<Run>> listAll() throws UserNotFoundException {
        User user = userService.getCurrentUser();
        List<Run> runs = runService.loadAll(user);
        return ResponseEntity.ok(runs);
    }

    @PostMapping(path="/runs", produces = "application/json")
    public ResponseEntity<Run> createRun(@RequestBody Run run) throws UserNotFoundException {
        User user = userService.getCurrentUser();
        run.setUser(user);
        Run created = runService.create(run);
        return ResponseEntity.ok(created);
    }

    @PutMapping(path="/runs/{id}", produces = "application/json")
    public ResponseEntity<Run> updateRun(@RequestBody Run run,@PathVariable Long id) throws UserNotFoundException {
        User user = userService.getCurrentUser();
        run.setUser(user);
        Run updated = runService.save(run);
        return ResponseEntity.ok(updated);
    }

}

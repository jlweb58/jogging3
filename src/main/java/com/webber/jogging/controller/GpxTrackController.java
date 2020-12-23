package com.webber.jogging.controller;

import com.webber.jogging.domain.GpxTrack;
import com.webber.jogging.domain.ParsedGpxTrack;
import com.webber.jogging.domain.Run;
import com.webber.jogging.domain.User;
import com.webber.jogging.service.GpxTrackService;
import com.webber.jogging.service.RunService;
import com.webber.jogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/jogging")
public class GpxTrackController {

    @Autowired
    private GpxTrackService gpxTrackService;

    @Autowired
    private UserService userService;

    @Autowired
    private RunService runService;

    @GetMapping(path = "/gpxtrack/{id}", produces = "application/json")
    public ResponseEntity<ParsedGpxTrack> getForRun(@PathVariable Long id) throws Exception {
            ParsedGpxTrack parsedGpxTrack = gpxTrackService.findForId(id);
            return ResponseEntity.ok(parsedGpxTrack);
    }

    @PostMapping(path = "/gpxtrack/{id}", produces = "application/json")
    public ResponseEntity<ParsedGpxTrack> saveGpxTrack(@RequestBody String gpxData, @PathVariable Long id) throws Exception {
        User user = userService.getCurrentUser();
        Run run = runService.find(id);
        GpxTrack gpxTrack;
        Optional<GpxTrack> optional = gpxTrackService.findUnparsedForId(id);
        if (optional.isEmpty()) {
            gpxTrack = new GpxTrack(gpxData, run, user);
        } else {
            gpxTrack = optional.get();
            gpxTrack.setGpxTrack(gpxData);
        }
        return ResponseEntity.ok(gpxTrackService.save(gpxTrack));
    }
}

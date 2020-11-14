package com.webber.jogging.controller;

import com.webber.jogging.domain.GpxTrack;
import com.webber.jogging.domain.ParsedGpxTrack;
import com.webber.jogging.domain.Run;
import com.webber.jogging.domain.User;
import com.webber.jogging.service.GpxTrackService;
import com.webber.jogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jogging")
public class GpxTrackController {

    @Autowired
    private GpxTrackService gpxTrackService;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/gpxtrack", produces = "application/json")
    public ResponseEntity<ParsedGpxTrack> getForRun(@RequestParam Long id) throws Exception {
            ParsedGpxTrack parsedGpxTrack = gpxTrackService.findForId(id);
            return ResponseEntity.ok(parsedGpxTrack);
    }

    @PostMapping(path = "/gpxtrack", produces = "application/json")
    public ResponseEntity<ParsedGpxTrack> saveGpxTrack(@RequestParam String gpxData, @RequestParam Run run) throws Exception {
        User user = userService.getCurrentUser();
        GpxTrack gpxTrack = new GpxTrack(gpxData, run, user);
        return ResponseEntity.ok(gpxTrackService.create(gpxTrack));
    }
}

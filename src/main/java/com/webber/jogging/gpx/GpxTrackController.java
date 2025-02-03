package com.webber.jogging.gpx;

import com.webber.jogging.activity.Activity;
import com.webber.jogging.domain.User;
import com.webber.jogging.activity.ActivityService;
import com.webber.jogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("/jogging")
public class GpxTrackController {

    @Autowired
    private GpxTrackService gpxTrackService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @GetMapping(path = "/gpxtrack/{id}", produces = "application/json")
    public ResponseEntity<ParsedGpxTrack> getForActivity(@PathVariable Long id) throws Exception {

        try {
            ParsedGpxTrack parsedGpxTrack = gpxTrackService.findForId(id);
            return ResponseEntity.ok(parsedGpxTrack);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/gpxtrack/{id}", produces = "application/json")
    public ResponseEntity<ParsedGpxTrack> saveGpxTrack(@RequestBody String gpxData, @PathVariable Long id) throws Exception {
        User user = userService.getCurrentUser();
        Activity activity = activityService.find(id);
        GpxTrack gpxTrack;
        Optional<GpxTrack> optional = gpxTrackService.findUnparsedForId(id);
        if (optional.isEmpty()) {
            gpxTrack = new GpxTrack(gpxData, activity, user);
        } else {
            gpxTrack = optional.get();
            gpxTrack.setGpxTrack(gpxData);
        }
        return ResponseEntity.ok(gpxTrackService.save(gpxTrack));
    }
}

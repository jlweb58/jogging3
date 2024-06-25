package com.webber.jogging.service;

import com.webber.jogging.Application;
import com.webber.jogging.domain.*;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import java.nio.charset.Charset;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
@Transactional
public class GpxTrackServiceImplTest {

    @Autowired
    private GpxTrackServiceImpl gpxTrackService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    private String testGpxTrack;

    @BeforeEach
    void setUp() throws Exception {
        testGpxTrack = IOUtils.toString(this.getClass().getResourceAsStream("/TestGpxTrack.gpx"), Charset.defaultCharset());
    }

    @Test
    public void testCreateAndFind() throws Exception {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Activity activity = activityService.create(Activity.build(new Date(), "Test", 5.2, new ActivityDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN));
        GpxTrack gpxTrack = new GpxTrack(testGpxTrack, activity, user);
        ParsedGpxTrack createdTrack = gpxTrackService.save(gpxTrack);
        assertNotNull(createdTrack);
        ParsedGpxTrack loaded = gpxTrackService.findForId(activity.getId());
        assertNotNull(loaded);
        assertEquals(4, loaded.getTrackElements().size());
    }

}

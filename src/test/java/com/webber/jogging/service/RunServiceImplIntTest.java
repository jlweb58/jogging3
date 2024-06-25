package com.webber.jogging.service;

import com.webber.jogging.Application;
import com.webber.jogging.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
@Transactional
public class RunServiceImplIntTest {

    @Autowired
    private RunService runService;

    @Autowired
    private ShoesService shoesService;

    @Autowired
    private UserService userService;

    @Test
    public void testCreateAndFind() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Run run = Run.build(new Date(), "Test", 5.2, new RunDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Run created = runService.create(run);
        assertNotNull(created);
        Run found = runService.find(created.getId());
        assertEquals(created, found);
    }

    @Test
    public void testLoadAllForUser() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Run run1 = Run.build(new Date(), "Test1", 5.2, new RunDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Run run2 = Run.build(new Date(), "Test2", 5.2, new RunDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Run run3 = Run.build(new Date(), "Test3", 5.2, new RunDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        runService.create(run1);
        runService.create(run2);
        runService.create(run3);
        List<Run> allRuns = runService.loadAll(user);
        assertNotNull(allRuns);
        assertEquals(3, allRuns.size());
        assertTrue(allRuns.contains(run1));
        assertTrue(allRuns.contains(run2));
        assertTrue(allRuns.contains(run3));
    }

    @Test
    public void testFilterForCourseAndUser() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Run run1 = Run.build(new Date(), "Test1", 5.2, new RunDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Run run2 = Run.build(new Date(), "Test2", 5.2, new RunDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Run run3 = Run.build(new Date(), "Test1", 5.2, new RunDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        runService.create(run1);
        runService.create(run2);
        runService.create(run3);
        RunFilter runFilter = new RunFilter("Test1", null, null, user);
        List<Run> runs = runService.search(runFilter);
        assertNotNull(runs);
        assertEquals(2, runs.size());
        assertTrue(runs.contains(run1));
        assertTrue(runs.contains(run3));
    }

    public void testFilterForCourseWrongUser() {
        User user1 = userService.create(new User("test1", "test", "test@test.com", true));
        User user2 = userService.create(new User("test2", "test", "test@test.com", true));
        Run run1 = Run.build(new Date(), "Test1", 5.2, new RunDuration(0, 31, 2), "13 Sunny", "blabla", 125, user1, ActivityType.RUN);
        Run run2 = Run.build(new Date(), "Test2", 5.2, new RunDuration(0, 31, 2), "13 Sunny", "blabla", 125, user1, ActivityType.RUN);
        Run run3 = Run.build(new Date(), "Test1", 5.2, new RunDuration(0, 31, 2), "13 Sunny", "blabla", 125, user1, ActivityType.RUN);
        runService.create(run1);
        runService.create(run2);
        runService.create(run3);
        RunFilter runFilter = new RunFilter("Test1", null, null, user2);
        List<Run> runs = runService.search(runFilter);
        assertNotNull(runs);
        assertEquals(0, runs.size());
    }

    @Test
    public void testFilterForStartAndEndDate() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Date date1 = java.sql.Date.valueOf(LocalDate.of(2019, Month.DECEMBER, 1));
        Date date2 = java.sql.Date.valueOf(LocalDate.of(2019, Month.NOVEMBER, 1));
        Date date3 = java.sql.Date.valueOf(LocalDate.of(2019, Month.OCTOBER, 1));
        Date queryDate = java.sql.Date.valueOf(LocalDate.of(2019, Month.OCTOBER, 31));
        Run run1 = Run.build(date1, "Test1", 5.2, new RunDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Run run2 = Run.build(date2, "Test2", 5.2, new RunDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Run run3 = Run.build(date3, "Test1", 5.2, new RunDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        runService.create(run1);
        runService.create(run2);
        runService.create(run3);
        RunFilter runFilter = new RunFilter("", queryDate, null, user);
        List<Run> runs = runService.search(runFilter);
        assertNotNull(runs);
        assertEquals(2, runs.size());
        assertTrue(runs.contains(run1));
        assertTrue(runs.contains(run2));
        runFilter = new RunFilter("", null, queryDate, user);
        runs = runService.search(runFilter);
        assertNotNull(runs);
        assertEquals(1, runs.size());
        assertTrue(runs.contains(run3));
    }

    @Test
    public void testFilterForStartAndEndDateAndCourse() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Date date1 = java.sql.Date.valueOf(LocalDate.of(2019, Month.DECEMBER, 1));
        Date date2 = java.sql.Date.valueOf(LocalDate.of(2019, Month.NOVEMBER, 1));
        Date date3 = java.sql.Date.valueOf(LocalDate.of(2019, Month.OCTOBER, 1));
        Date queryDate = java.sql.Date.valueOf(LocalDate.of(2019, Month.OCTOBER, 31));
        Run run1 = Run.build(date1, "Test1", 5.2, new RunDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Run run2 = Run.build(date2, "Test2", 5.2, new RunDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Run run3 = Run.build(date3, "Test1", 5.2, new RunDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        runService.create(run1);
        runService.create(run2);
        runService.create(run3);
        RunFilter runFilter = new RunFilter("Test2", queryDate, null, user);
        List<Run> runs = runService.search(runFilter);
        assertNotNull(runs);
        assertEquals(1, runs.size());
        assertTrue(runs.contains(run2));
        runFilter = new RunFilter("Test1", null, queryDate, user);
        runs = runService.search(runFilter);
        assertNotNull(runs);
        assertEquals(1, runs.size());
        assertTrue(runs.contains(run3));
    }

    @Test
    public void testGetDistanceForDateRange() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Date date1 = java.sql.Date.valueOf(LocalDate.of(2019, Month.DECEMBER, 1));
        Date date2 = java.sql.Date.valueOf(LocalDate.of(2019, Month.NOVEMBER, 1));
        Date date3 = java.sql.Date.valueOf(LocalDate.of(2019, Month.OCTOBER, 1));
        Date queryDate = java.sql.Date.valueOf(LocalDate.of(2019, Month.OCTOBER, 31));
        Run run1 = Run.build(date1, "Test1", 5.2, new RunDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Run run2 = Run.build(date2, "Test2", 5.2, new RunDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Run run3 = Run.build(date3, "Test1", 5.2, new RunDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        runService.create(run1);
        runService.create(run2);
        runService.create(run3);
        RunFilter runFilter = new RunFilter("", queryDate, null, user);
        double totalDistance = runService.getDistanceForDateRange(date3, date1, user);
        assertEquals(10.4, totalDistance, 0.01);

    }




}

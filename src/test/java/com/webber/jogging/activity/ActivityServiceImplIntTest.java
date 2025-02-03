package com.webber.jogging.activity;

import com.webber.jogging.Application;
import com.webber.jogging.domain.*;
import com.webber.jogging.gear.GearService;
import com.webber.jogging.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
@Transactional
public class ActivityServiceImplIntTest {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private GearService gearService;

    @Autowired
    private UserService userService;

    @Test
    public void testCreateAndFind() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Activity activity = Activity.build(new Date(), "Test", 5.2, new ActivityDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Activity created = activityService.create(activity);
        assertNotNull(created);
        Activity found = activityService.find(created.getId());
        assertEquals(created, found);
    }

    @Test
    public void testLoadAllForUser() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Activity activity1 = Activity.build(new Date(), "Test1", 5.2, new ActivityDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Activity activity2 = Activity.build(new Date(), "Test2", 5.2, new ActivityDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Activity activity3 = Activity.build(new Date(), "Test3", 5.2, new ActivityDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        activityService.create(activity1);
        activityService.create(activity2);
        activityService.create(activity3);
        List<Activity> allActivities = activityService.loadAll(user);
        assertNotNull(allActivities);
        assertEquals(3, allActivities.size());
        assertTrue(allActivities.contains(activity1));
        assertTrue(allActivities.contains(activity2));
        assertTrue(allActivities.contains(activity3));
    }

    @Test
    public void testFilterForCourseAndUser() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Activity activity1 = Activity.build(new Date(), "Test1", 5.2, new ActivityDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Activity activity2 = Activity.build(new Date(), "Test2", 5.2, new ActivityDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Activity activity3 = Activity.build(new Date(), "Test1", 5.2, new ActivityDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        activityService.create(activity1);
        activityService.create(activity2);
        activityService.create(activity3);
        ActivityFilter activityFilter = new ActivityFilter("Test1", null, null, user);
        List<Activity> activities = activityService.search(activityFilter);
        assertNotNull(activities);
        assertEquals(2, activities.size());
        assertTrue(activities.contains(activity1));
        assertTrue(activities.contains(activity3));
    }

    public void testFilterForCourseWrongUser() {
        User user1 = userService.create(new User("test1", "test", "test@test.com", true));
        User user2 = userService.create(new User("test2", "test", "test@test.com", true));
        Activity activity1 = Activity.build(new Date(), "Test1", 5.2, new ActivityDuration(0, 31, 2), "13 Sunny", "blabla", 125, user1, ActivityType.RUN);
        Activity activity2 = Activity.build(new Date(), "Test2", 5.2, new ActivityDuration(0, 31, 2), "13 Sunny", "blabla", 125, user1, ActivityType.RUN);
        Activity activity3 = Activity.build(new Date(), "Test1", 5.2, new ActivityDuration(0, 31, 2), "13 Sunny", "blabla", 125, user1, ActivityType.RUN);
        activityService.create(activity1);
        activityService.create(activity2);
        activityService.create(activity3);
        ActivityFilter activityFilter = new ActivityFilter("Test1", null, null, user2);
        List<Activity> activities = activityService.search(activityFilter);
        assertNotNull(activities);
        assertEquals(0, activities.size());
    }

    @Test
    public void testFilterForStartAndEndDate() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Date date1 = java.sql.Date.valueOf(LocalDate.of(2019, Month.DECEMBER, 1));
        Date date2 = java.sql.Date.valueOf(LocalDate.of(2019, Month.NOVEMBER, 1));
        Date date3 = java.sql.Date.valueOf(LocalDate.of(2019, Month.OCTOBER, 1));
        Date queryDate = java.sql.Date.valueOf(LocalDate.of(2019, Month.OCTOBER, 31));
        Activity activity1 = Activity.build(date1, "Test1", 5.2, new ActivityDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Activity activity2 = Activity.build(date2, "Test2", 5.2, new ActivityDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Activity activity3 = Activity.build(date3, "Test1", 5.2, new ActivityDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        activityService.create(activity1);
        activityService.create(activity2);
        activityService.create(activity3);
        ActivityFilter activityFilter = new ActivityFilter("", queryDate, null, user);
        List<Activity> activities = activityService.search(activityFilter);
        assertNotNull(activities);
        assertEquals(2, activities.size());
        assertTrue(activities.contains(activity1));
        assertTrue(activities.contains(activity2));
        activityFilter = new ActivityFilter("", null, queryDate, user);
        activities = activityService.search(activityFilter);
        assertNotNull(activities);
        assertEquals(1, activities.size());
        assertTrue(activities.contains(activity3));
    }

    @Test
    public void testFilterForStartAndEndDateAndCourse() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Date date1 = java.sql.Date.valueOf(LocalDate.of(2019, Month.DECEMBER, 1));
        Date date2 = java.sql.Date.valueOf(LocalDate.of(2019, Month.NOVEMBER, 1));
        Date date3 = java.sql.Date.valueOf(LocalDate.of(2019, Month.OCTOBER, 1));
        Date queryDate = java.sql.Date.valueOf(LocalDate.of(2019, Month.OCTOBER, 31));
        Activity activity1 = Activity.build(date1, "Test1", 5.2, new ActivityDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Activity activity2 = Activity.build(date2, "Test2", 5.2, new ActivityDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Activity activity3 = Activity.build(date3, "Test1", 5.2, new ActivityDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        activityService.create(activity1);
        activityService.create(activity2);
        activityService.create(activity3);
        ActivityFilter activityFilter = new ActivityFilter("Test2", queryDate, null, user);
        List<Activity> activities = activityService.search(activityFilter);
        assertNotNull(activities);
        assertEquals(1, activities.size());
        assertTrue(activities.contains(activity2));
        activityFilter = new ActivityFilter("Test1", null, queryDate, user);
        activities = activityService.search(activityFilter);
        assertNotNull(activities);
        assertEquals(1, activities.size());
        assertTrue(activities.contains(activity3));
    }

    @Test
    public void testGetDistanceForDateRange() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Date date1 = java.sql.Date.valueOf(LocalDate.of(2019, Month.DECEMBER, 1));
        Date date2 = java.sql.Date.valueOf(LocalDate.of(2019, Month.NOVEMBER, 1));
        Date date3 = java.sql.Date.valueOf(LocalDate.of(2019, Month.OCTOBER, 1));
        Date queryDate = java.sql.Date.valueOf(LocalDate.of(2019, Month.OCTOBER, 31));
        Activity activity1 = Activity.build(date1, "Test1", 5.2, new ActivityDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Activity activity2 = Activity.build(date2, "Test2", 5.2, new ActivityDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        Activity activity3 = Activity.build(date3, "Test1", 5.2, new ActivityDuration(0, 31, 2), "13 Sunny", "blabla", 125, user, ActivityType.RUN);
        activityService.create(activity1);
        activityService.create(activity2);
        activityService.create(activity3);
        ActivityFilter activityFilter = new ActivityFilter("", queryDate, null, user);
        double totalDistance = activityService.getDistanceForDateRange(date3, date1, user);
        assertEquals(10.4, totalDistance, 0.01);

    }




}

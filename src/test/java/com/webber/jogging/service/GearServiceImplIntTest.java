package com.webber.jogging.service;

import com.webber.jogging.Application;
import com.webber.jogging.activity.Activity;
import com.webber.jogging.activity.ActivityDuration;
import com.webber.jogging.activity.ActivityService;
import com.webber.jogging.activity.ActivityType;
import com.webber.jogging.domain.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
@Transactional
public class GearServiceImplIntTest {

    @Autowired
    private GearService gearService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Test
    public void testCreateAndFind() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Gear gear = new Gear("test", 0.0, user);
        Gear created = gearService.create(gear);
        assertNotNull(created);
        Gear found = gearService.find(created.getId());
        assertEquals(created, found);
        user = userService.find("test");
        List<Gear> userShoes = user.getGear();
        assertNotNull(userShoes);
        assertEquals(1, userShoes.size());
        assertEquals(found, userShoes.get(0));
    }

    @Test
    public void testGetShoesForUser() {
        User user = userService.create(new User("test", "test", "test", true));
        Gear gear1 = new Gear("MyShoes1", 13.5, user);
        Gear gear2 = new Gear("MyShoes2", 23.5, user);
        Gear gear3 = new Gear("MyShoes3", 33.5, user);
        gear3.setActive(false);
        gearService.create(gear1);
        gearService.create(gear2);
        gearService.create(gear3);
        //Test getting only the active shoes
        List<Gear> allShoes = gearService.getGearForUser(user, true);
        assertNotNull(allShoes);
        assertEquals(2, allShoes.size());
        assertTrue(allShoes.contains(gear1));
        assertTrue(allShoes.contains(gear2));
        //Test that we get all the shoes
        allShoes = gearService.getGearForUser(user, false);
        assertNotNull(allShoes);
        assertEquals(3, allShoes.size());
        assertTrue(allShoes.contains(gear1));
        assertTrue(allShoes.contains(gear2));
        assertTrue(allShoes.contains(gear3));
    }

    @Test
    public void testMileageOffset() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Gear gear = new Gear("test", 10.0, user);
        Gear created = gearService.create(gear);
        assertEquals(10.0, created.getMileage(), 0.01);
    }

    @Test
    public void testGetShoesForUserMileage() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Gear gear = new Gear("MyShoes1", 0, user);
        ActivityDuration activityDuration = new ActivityDuration(0, 28, 33);
        Activity activity = Activity.build(new Date(), "Mü2", 5.2, activityDuration, "15 pc", "Felt good", 140, user, ActivityType.RUN);
        activity.setGear(gear);
        activityService.create(activity);
        gear = gearService.create(gear);
        assertEquals(5.2, gear.getMileage(), 0.01);
        gear.setMileageOffset(10.0);
        gear = gearService.save(gear);
        assertEquals(15.2, gear.getMileage(), 0.01);
    }


    @Test
    public void testSaveShoes() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Gear gear = new Gear("test", 0.0, user);
        Gear created = gearService.create(gear);
        gear.setMileageOffset(10.0);
        Gear saved = gearService.save(gear);
        assertEquals(10.0, saved.getMileage(), 0.01);
        List<Gear> userShoes = user.getGear();
        assertNotNull(userShoes);
        assertEquals(1, userShoes.size());
        assertEquals(saved, userShoes.get(0));
    }

}

package com.webber.jogging.service;

import com.webber.jogging.Application;
import com.webber.jogging.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
@Transactional
public class ShoesServiceImplIntTest {

    @Autowired
    private ShoesService shoesService;

    @Autowired
    private UserService userService;

    @Autowired
    private RunService runService;

    @Test
    public void testCreateAndFind() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Shoes shoes = new Shoes("test", 0.0, user);
        Shoes created = shoesService.create(shoes);
        assertNotNull(created);
        Shoes found = shoesService.find(created.getId());
        assertEquals(created, found);
        user = userService.find("test");
        List<Shoes> userShoes = user.getShoes();
        assertNotNull(userShoes);
        assertEquals(1, userShoes.size());
        assertEquals(found, userShoes.get(0));
    }

    @Test
    public void testGetShoesForUser() {
        User user = userService.create(new User("test", "test", "test", true));
        Shoes shoes1 = new Shoes("MyShoes1", 13.5, user);
        Shoes shoes2 = new Shoes("MyShoes2", 23.5, user);
        Shoes shoes3 = new Shoes("MyShoes3", 33.5, user);
        shoes3.setActive(false);
        shoesService.create(shoes1);
        shoesService.create(shoes2);
        shoesService.create(shoes3);
        //Test getting only the active shoes
        List<Shoes> allShoes = shoesService.getShoesForUser(user, true);
        assertNotNull(allShoes);
        assertEquals(2, allShoes.size());
        assertTrue(allShoes.contains(shoes1));
        assertTrue(allShoes.contains(shoes2));
        //Test that we get all the shoes
        allShoes = shoesService.getShoesForUser(user, false);
        assertNotNull(allShoes);
        assertEquals(3, allShoes.size());
        assertTrue(allShoes.contains(shoes1));
        assertTrue(allShoes.contains(shoes2));
        assertTrue(allShoes.contains(shoes3));
    }

    @Test
    public void testMileageOffset() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Shoes shoes = new Shoes("test", 10.0, user);
        Shoes created = shoesService.create(shoes);
        assertEquals(10.0, created.getMileage(), 0.01);
    }

    @Test
    public void testGetShoesForUserMileage() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Shoes shoes = new Shoes("MyShoes1", 0, user);
        RunDuration runDuration = new RunDuration(0, 28, 33);
        Run run = Run.build(new Date(), "Mü2", 5.2, runDuration, "15 pc", "Felt good", 140, user, ActivityType.RUN);
        run.setShoes(shoes);
        runService.create(run);
        shoes = shoesService.create(shoes);
        assertEquals(5.2, shoes.getMileage(), 0.0);
        shoes.setMileageOffset(10.0);
        shoes = shoesService.save(shoes);
        assertEquals(15.2, shoes.getMileage(), 0.0);
    }


    @Test
    public void testSaveShoes() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        Shoes shoes = new Shoes("test", 0.0, user);
        Shoes created = shoesService.create(shoes);
        shoes.setMileageOffset(10.0);
        Shoes saved = shoesService.save(shoes);
        assertEquals(10.0, saved.getMileage(), 0.01);
        List<Shoes> userShoes = user.getShoes();
        assertNotNull(userShoes);
        assertEquals(1, userShoes.size());
        assertEquals(saved, userShoes.get(0));
    }

}

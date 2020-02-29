package com.webber.jogging.service;

import com.webber.jogging.Application;
import com.webber.jogging.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class UserServiceImplIntTest {
    @Autowired
    private UserService userService;

    private User createUserWithTransaction(User user) {
        TestTransaction.end();
        TestTransaction.start();
        User newUser = userService.create(user);
        TestTransaction.end();
        return newUser;
    }

    @Test
    public void testCreateAndFind() {
        User user = new User("test", "test", "test@test.com", true);
        User created = userService.create(user);
        assertNotNull(created);
        User found = userService.find("test");
        assertEquals(created, found);
    }

    @Test
    public void testCreateUserIdNotNull() {
        User user = new User("test", "test", "test@test.com", true);
        User created = userService.create(user);
        try {
            userService.create(created);
            fail("Should have been an IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            //expected
        }
    }

    @Test
    public void testChangePassword() {
        User user = userService.create(new User("test", "FlixBus2012", "test@test.com", true));
        String encryptedPasswordBefore = user.getPassword();
        userService.changePassword(user, "FlixBus2012", "notest");
        assertNotEquals(encryptedPasswordBefore, user.getPassword());
    }

    @Test
    public void testChangePasswordWrongOldPassword() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        String encryptedPasswordBefore = user.getPassword();
        try {
            userService.changePassword(user, "testy", "notest");
            fail("Should have been a SecurityException");
        } catch (SecurityException expected) {
            //expected
        }
    }

    @Test
    public void testCreateUserWithSameUsername() {
        User user1 = createUserWithTransaction(new User("test", "FlixBus2012", "test@test.com", true));
        try {
            User user2 = createUserWithTransaction(new User("test", "FlixBus2012", "test@test.com", true));
            fail("Should have been an exception");
        } catch (Exception expected) {
            expected.printStackTrace();
        }
    }



}

package com.webber.jogging.strava.service;

import com.webber.jogging.Application;
import com.webber.jogging.user.UserService;
import com.webber.jogging.strava.StravaAuthentication;
import com.webber.jogging.user.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
@Transactional
public class StravaAuthenticationServiceImplTest {

    @Autowired
    private StravaAuthenticationService service;

    @Autowired
    private UserService userService;

    @Test
    public void testCreateAndFindByUser() {
        User user = userService.create(new User("test", "test", "test@test.com", true));
        LocalDateTime now = LocalDateTime.now();
        StravaAuthentication authentication = new StravaAuthentication("12345", "67890", user, now);
        authentication = service.create(authentication);
        assertNotNull(authentication);
        StravaAuthentication foundAuthentication = service.findByUser(user);
        assertNotNull(foundAuthentication);
        assertEquals(foundAuthentication.getExpirationDate(), now);
        assertEquals(foundAuthentication.getUser(), user);
        assertEquals("12345", foundAuthentication.getAccessToken());
        assertEquals("67890", foundAuthentication.getRefreshToken());
        assertNotNull(foundAuthentication.getId());
    }

}

package com.webber.jogging.service;

import com.webber.jogging.Application;
import com.webber.jogging.domain.StravaAuthentication;
import com.webber.jogging.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

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
        assertEquals(foundAuthentication.getAccessToken(), "12345");
        assertEquals(foundAuthentication.getRefreshToken(), "67890");
        assertNotNull(foundAuthentication.getId());
    }

    @Test
    public void testObtainAccessTokenIntTest() {
        User user = userService.create(new User("test", "test", "test@test.com", true));

        StravaAuthentication response = service.obtainAccessToken(user);
        assertNotNull(response);
        System.out.println(response);

    }

}

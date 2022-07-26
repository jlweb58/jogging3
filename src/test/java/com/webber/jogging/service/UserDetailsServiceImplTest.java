package com.webber.jogging.service;

import com.webber.jogging.Application;
import com.webber.jogging.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
@Transactional
public class UserDetailsServiceImplTest {

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    private String username = "username";

    @Test
    public void loadUserByUsername() {
        User user = new User(username, "test", "test@test.com", true);
        User created = userService.create(user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }

    @Test
    public void loadUserByUsernameNotFound() {
        assertThrows(UsernameNotFoundException.class, () -> {
        userDetailsService.loadUserByUsername("foo");
        });
    }
}

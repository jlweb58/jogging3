package com.webber.jogging.service;

import com.webber.jogging.controller.UserNotFoundException;
import com.webber.jogging.domain.User;
import com.webber.jogging.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User find(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User create(User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("User with username " + user.getUsername() + " already exists");
        }
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public void changePassword(User user, String oldPassword, String newPassword) {
        if (newPassword == null) {
            throw new SecurityException("New password may not be empty");
        }
        user = userRepository.findById((Long) user.getId()).get();
        String oldPasswordEncoded = passwordEncoder.encode(oldPassword);
        boolean matches = passwordEncoder.matches(oldPassword, user.getPassword());
        if (!matches) {
            throw new SecurityException("Old password incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
    }

    /**
     * Load the current user from the security context, if none exists throw an exception.
     *
     * @return The current user from the security context
     * @throws UserNotFoundException if there is no current user
     */
    public User getCurrentUser() throws UserNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        LOG.info("Current user is " + username);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("No user found for username " + username);
        }
        return user;
    }



}

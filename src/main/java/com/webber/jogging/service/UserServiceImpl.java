package com.webber.jogging.service;

import com.webber.jogging.domain.User;
import com.webber.jogging.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User find(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User create(User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("User with username " + user.getUsername() + " already exists");
        }
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        return repository.save(user);
    }

    @Override
    public void changePassword(User user, String oldPassword, String newPassword) {
        if (newPassword == null) {
            throw new SecurityException("New password may not be empty");
        }
        user = repository.findById((Long) user.getId()).get();
        String oldPasswordEncoded = passwordEncoder.encode(oldPassword);
        boolean matches = passwordEncoder.matches(oldPassword, user.getPassword());
        if (!matches) {
            throw new SecurityException("Old password incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
    }


}

package com.webber.jogging.service;

import com.webber.jogging.domain.StravaAuthentication;
import com.webber.jogging.domain.User;
import com.webber.jogging.repository.StravaAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StravaAuthenticationServiceImpl implements StravaAuthenticationService {

    private final StravaAuthenticationRepository repository;

    @Autowired
    public StravaAuthenticationServiceImpl(StravaAuthenticationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void refreshAccessToken() {

    }

    @Override
    public StravaAuthentication findByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public StravaAuthentication create(StravaAuthentication authentication) {
        return repository.save(authentication);
    }
}

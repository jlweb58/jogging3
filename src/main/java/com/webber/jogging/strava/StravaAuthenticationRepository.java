package com.webber.jogging.strava;

import com.webber.jogging.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StravaAuthenticationRepository extends JpaRepository<StravaAuthentication, Long> {

    StravaAuthentication findByUser(User user);
}

package com.webber.jogging.strava;

import com.webber.jogging.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StravaAuthenticationRepository extends JpaRepository<StravaAuthentication, Long> {

    StravaAuthentication findByUser(User user);
}

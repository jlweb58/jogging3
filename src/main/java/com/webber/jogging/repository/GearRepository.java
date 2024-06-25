package com.webber.jogging.repository;

import com.webber.jogging.domain.Gear;
import com.webber.jogging.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GearRepository extends JpaRepository<Gear, Long> {

    @Query("Select sum(distance) from Activity r where r.gear = :gear")
    Double getMileageForGear(@Param("gear") Gear gear);

    List<Gear> findByUser(User user);
}

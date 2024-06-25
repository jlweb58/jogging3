package com.webber.jogging.repository;

import com.webber.jogging.domain.Activity;
import com.webber.jogging.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long>, JpaSpecificationExecutor<Activity> {

    List<Activity> findAllByUserOrderByDateDesc(User user);

}



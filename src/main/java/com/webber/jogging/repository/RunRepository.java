package com.webber.jogging.repository;

import com.webber.jogging.domain.Run;
import com.webber.jogging.domain.RunFilter;
import com.webber.jogging.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RunRepository extends JpaRepository<Run, Long>, JpaSpecificationExecutor<Run> {

    List<Run> findAllByUserOrderByDateDesc(User user);

}



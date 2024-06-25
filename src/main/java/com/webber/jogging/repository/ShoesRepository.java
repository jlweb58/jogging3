package com.webber.jogging.repository;

import com.webber.jogging.domain.Shoes;
import com.webber.jogging.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShoesRepository extends JpaRepository<Shoes, Long> {

    @Query("Select sum(distance) from Activity r where r.shoes = :shoes")
    Double getMileageForShoes(@Param("shoes") Shoes shoes);

    List<Shoes> findByUser(User user);
}

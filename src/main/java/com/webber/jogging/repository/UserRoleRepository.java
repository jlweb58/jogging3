package com.webber.jogging.repository;

import com.webber.jogging.domain.Role;
import com.webber.jogging.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByRole(Role role);
}

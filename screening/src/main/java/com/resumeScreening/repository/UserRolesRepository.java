package com.resumeScreening.repository;

import com.resumeScreening.exception.UserNotFoundException;
import com.resumeScreening.model.SignUpTable;
import com.resumeScreening.model.UserRoles;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRolesRepository extends JpaRepository<UserRoles,Long> {
    
    Optional<UserRoles> findByRoleCode(String roleCode);
}

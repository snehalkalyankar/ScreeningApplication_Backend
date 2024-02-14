package com.resumeScreening.repository;

import com.resumeScreening.model.UserRoles;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRolesRepository extends JpaRepository<UserRoles,Long> {
    
    public Optional<UserRoles> findByRoleCode(String roleCode);
}

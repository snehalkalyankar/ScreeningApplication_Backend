package com.resumeScreening.repository;

import com.resumeScreening.model.LoginTable;
import com.resumeScreening.model.SignUpTable;
import com.resumeScreening.model.UserRoles;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRolesRepository extends JpaRepository<SignUpTable,Long> {
    Optional<SignUpTable> findByEmail(String email);
    
    public Optional<UserRoles> findByRoleCode(String roleCode);
}

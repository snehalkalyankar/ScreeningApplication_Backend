package com.resumeScreening.repository;

import com.resumeScreening.model.LoginTable;
import com.resumeScreening.model.SignUpTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SignUpTable,Long> {
    Optional<SignUpTable> findByEmail(String email);
}

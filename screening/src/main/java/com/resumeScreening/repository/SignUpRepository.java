package com.resumeScreening.repository;

import com.resumeScreening.exception.UserNotFoundException;
import com.resumeScreening.model.SignUpTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SignUpRepository extends JpaRepository<SignUpTable, Long> {
    Optional<SignUpTable> findByEmail(String email);

    SignUpTable findByOtpAndEmail(Long otp, String email);
    SignUpTable findByOtp(Long otp);

}


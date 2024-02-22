package com.resumeScreening.repository;

import com.resumeScreening.exception.UserNotFoundException;
import com.resumeScreening.model.LoginTable;
import com.resumeScreening.model.SignUpTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SignUpRepository extends JpaRepository<SignUpTable, Long> {
    Optional<SignUpTable> findByEmail(String email);
    Optional<SignUpTable> findByLogin(LoginTable login);

    SignUpTable findByOtpAndEmail(Long otp, String email);
    SignUpTable findByOtp(Long otp);
    

}


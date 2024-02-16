package com.resumeScreening.service;

import com.resumeScreening.exception.UserNotFoundException;
import com.resumeScreening.model.LoginTable;
import com.resumeScreening.model.SignUpTable;
import com.resumeScreening.repository.SignUpRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService{
    private final SignUpRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ForgotPasswordServiceImpl(SignUpRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SignUpTable forgotPassword(String email) throws UserNotFoundException {
        Optional<SignUpTable> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        SignUpTable user = userOptional.get();
        user.setPasswordResetToken(generateToken());

        user = userRepository.save(user);
        return user;
    }

    @Override
    public LoginTable resetPassword(String token, String password) throws UserNotFoundException {
        Optional<SignUpTable> userOptional = Optional.ofNullable(userRepository.findByResetPasswordToken(token));

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        SignUpTable user = userOptional.get();
        LoginTable loginTable = user.getLogin();

       loginTable.setPassword(password);
        loginTable.setPassword(passwordEncoder.encode(loginTable.getPassword()));
        user.setPasswordResetToken(null);

        userRepository.save(user);

        return loginTable;
    }

    @Override
    public String generateToken() {
        return UUID.randomUUID() + UUID.randomUUID().toString();
    }
}

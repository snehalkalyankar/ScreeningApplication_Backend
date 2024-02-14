package com.resumeScreening.service;

import com.resumeScreening.exception.UserNotFoundException;
import com.resumeScreening.model.LoginTable;
import com.resumeScreening.model.SignUpTable;
import com.resumeScreening.repository.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRolesRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SignUpTable updatePassword(String currentPassword, String newPassword, String email) throws UserNotFoundException {
        Optional<SignUpTable> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        SignUpTable existingUser = user.get();
        LoginTable login = existingUser.getLogin();
        boolean isCurrentPasswordAndExistingPasswordMatches = passwordEncoder.matches(login.getPassword(), currentPassword);
        if (login.getPassword() != null) {
            if (!isCurrentPasswordAndExistingPasswordMatches) {
                throw new RuntimeException("Password does not match");
            }
            login.setPassword(newPassword);
            login.setPassword(passwordEncoder.encode(newPassword));
            existingUser.setLogin(login);
        }
        return userRepository.save(existingUser);
    }
}

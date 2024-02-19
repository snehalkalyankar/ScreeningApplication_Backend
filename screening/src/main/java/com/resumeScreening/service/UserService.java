package com.resumeScreening.service;

import com.resumeScreening.bean.JWTRequest;
import com.resumeScreening.bean.JWTResponse;
import com.resumeScreening.dto.SignUpDto;
import com.resumeScreening.exception.AuthorizationException;
import com.resumeScreening.exception.UserNotFoundException;
import com.resumeScreening.exception.UserSignupException;
import com.resumeScreening.model.SignUpTable;

public interface UserService {

    SignUpTable updatePassword(String currentPassword, String newPassword, String email) throws UserNotFoundException;

    String SaveSignUp(SignUpDto bean) throws UserSignupException;

    JWTResponse validateLogin(JWTRequest request) throws AuthorizationException;
    SignUpTable getUser(String email) throws UserNotFoundException;

    Long generateOtp(String email) throws UserNotFoundException;

    void saveOtp(String email, Long otp) throws UserNotFoundException;

    SignUpTable forgotPassword(Long otp, String password) throws UserNotFoundException;
    String validateOTP(Long otp) throws UserNotFoundException;
}

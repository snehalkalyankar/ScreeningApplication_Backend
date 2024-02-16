package com.resumeScreening.service;

import com.resumeScreening.bean.JWTRequest;
import com.resumeScreening.bean.JWTResponse;
import com.resumeScreening.dto.SignUpDto;
import com.resumeScreening.exception.AuthorizationException;
import com.resumeScreening.exception.UserNotFoundException;
import com.resumeScreening.exception.UserSignupException;
import com.resumeScreening.model.LoginTable;
import com.resumeScreening.model.SignUpTable;

public interface UserService {

    SignUpTable updatePassword(String currentPassword, String newPassword, String email) throws UserNotFoundException;

    String SaveSignUp(SignUpDto bean) throws UserSignupException;

    JWTResponse validateLogin(JWTRequest request) throws AuthorizationException;

    SignUpTable forgotPassword(String email) throws UserNotFoundException;

    LoginTable resetPassword(String token, String password) throws UserNotFoundException;

    String generateToken();
}

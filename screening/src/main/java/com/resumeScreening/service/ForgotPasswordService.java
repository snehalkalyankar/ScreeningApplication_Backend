package com.resumeScreening.service;


import com.resumeScreening.exception.UserNotFoundException;
import com.resumeScreening.model.LoginTable;
import com.resumeScreening.model.SignUpTable;

public interface ForgotPasswordService {
    SignUpTable forgotPassword(String email) throws UserNotFoundException;
    LoginTable resetPassword(String token, String password) throws UserNotFoundException;
    String generateToken();
}

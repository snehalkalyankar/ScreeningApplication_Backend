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
    
    public String SaveSignUp(SignUpDto bean)throws UserSignupException;
    
    public JWTResponse validateLogin(JWTRequest request) throws AuthorizationException;
}

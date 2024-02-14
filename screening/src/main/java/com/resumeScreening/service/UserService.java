package com.resumeScreening.service;

import com.resumeScreening.dto.SignUpDto;
import com.resumeScreening.exception.UserNotFoundException;
import com.resumeScreening.model.SignUpTable;

public interface UserService {

    SignUpTable updatePassword(String currentPassword, String newPassword, String email) throws UserNotFoundException;
    
    public String SaveSignUp(SignUpDto bean)throws Exception;
}

package com.resumeScreening.service;

import com.resumeScreening.exception.UserNotFoundException;
import com.resumeScreening.model.SignUpTable;

public interface UserService {

    SignUpTable updatePassword(String currentPassword, String newPassword, String email) throws UserNotFoundException;
}

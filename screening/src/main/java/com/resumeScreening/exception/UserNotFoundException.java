package com.resumeScreening.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotFoundException extends Exception {
    private static final long serialVersionUID = 2L;

    public UserNotFoundException(String message) {
        super(message);
    }
}

package com.resumeScreening.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "This User in not present in database")
public class UserNotFoundException extends Exception {
}

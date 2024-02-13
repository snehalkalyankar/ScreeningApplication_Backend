package com.resumeScreening.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "This User is already present in database")
public class UserAlreadyPresentException extends Exception {
}

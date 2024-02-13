package com.resumeScreening.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "This role is already available in database")
public class RoleAlreadyPresentException extends Exception {
}

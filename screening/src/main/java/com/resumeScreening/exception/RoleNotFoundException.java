package com.resumeScreening.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "This Role is Not available in database")
public class RoleNotFoundException extends Exception {
}

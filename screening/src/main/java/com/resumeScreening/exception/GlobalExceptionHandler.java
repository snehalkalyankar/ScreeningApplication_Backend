package com.resumeScreening.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<ExceptionResponse> handleAuthException(
			BadCredentialsException authorizationException, final HttpServletRequest request) {
		
		ExceptionResponse errorResponse = new ExceptionResponse();
		
		String statusMessage = HttpStatus.UNAUTHORIZED.toString();
		errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		errorResponse.setExceptionType(authorizationException.getClass().getName());
		errorResponse.setMessage(statusMessage.substring(statusMessage.indexOf(" ") + 1));
		errorResponse.setExceptionMessege(authorizationException.getMessage());
		errorResponse.setPath(request.getRequestURI());
		errorResponse.setTimeStamp(LocalDateTime.now());
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
	}
	
	@ExceptionHandler(UserSignupException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponse> handleSignupException(
	        UserSignupException signupException, final HttpServletRequest request) {
	    
	    ExceptionResponse errorResponse = new ExceptionResponse();
	    String statusMessage = HttpStatus.BAD_REQUEST.toString();
	    errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
	    errorResponse.setExceptionType(signupException.getClass().getName());
	    errorResponse.setMessage(statusMessage.substring(statusMessage.indexOf(" ") + 1));
	    errorResponse.setExceptionMessege(signupException.getMessage());
	    errorResponse.setPath(request.getRequestURI());
	    errorResponse.setTimeStamp(LocalDateTime.now());
	    
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	
	
}

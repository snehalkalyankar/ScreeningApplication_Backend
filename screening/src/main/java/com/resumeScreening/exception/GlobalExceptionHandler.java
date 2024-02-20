package com.resumeScreening.exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.resumeScreening.controller.AuthenticationController;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	  private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(AuthorizationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<ExceptionResponse> handleAuthException(
			AuthorizationException authorizationException, final HttpServletRequest request) {
		
		ExceptionResponse errorResponse = new ExceptionResponse();
		
		String statusMessage = HttpStatus.UNAUTHORIZED.toString();
		errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		errorResponse.setExceptionType(authorizationException.getClass().getName());
		errorResponse.setMessage("Authentication Failed");
		errorResponse.setExceptionMessage(statusMessage.substring(statusMessage.indexOf(" ") + 1));
		errorResponse.setPath(request.getRequestURI());
		errorResponse.setTimeStamp(LocalDateTime.now());
		logger.error("Authentication Failed", authorizationException);
	//	logger.debug("Authentication Failed", authorizationException); 
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
	    errorResponse.setExceptionMessage(signupException.getMessage());
	    errorResponse.setPath(request.getRequestURI());
	    errorResponse.setTimeStamp(LocalDateTime.now());
	    logger.error(statusMessage.substring(statusMessage.indexOf(" ") + 1), signupException);
	  //  logger.debug(statusMessage.substring(statusMessage.indexOf(" ") + 1), signupException);
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponse> handleUserNotFoundException(
			UserNotFoundException userNotFoundException, final HttpServletRequest request) {

		ExceptionResponse errorResponse = new ExceptionResponse();
		String statusMessage = HttpStatus.BAD_REQUEST.toString();
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setExceptionType(userNotFoundException.getClass().getName());
		errorResponse.setMessage(statusMessage.substring(statusMessage.indexOf(" ") + 1));
		errorResponse.setExceptionMessage(userNotFoundException.getMessage());
		errorResponse.setPath(request.getRequestURI());
		errorResponse.setTimeStamp(LocalDateTime.now());
		logger.error(statusMessage.substring(statusMessage.indexOf(" ") + 1), userNotFoundException);
		//  logger.debug(statusMessage.substring(statusMessage.indexOf(" ") + 1), userNotFoundException);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
}

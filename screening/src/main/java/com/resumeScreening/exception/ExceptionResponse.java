package com.resumeScreening.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {

	private int status;
	
	private String message;
	
	private String exceptionMessege;
	
	private String exceptionType;
	
	private String path;
	
	private LocalDateTime timeStamp;

	public String getExceptionMessege() {
		return exceptionMessege;
	}

	public void setExceptionMessege(String exceptionMessege) {
		this.exceptionMessege = exceptionMessege;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	
}

package com.prashik.users.model;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ApiResponse {

	private String message;
	private boolean success;
	private HttpStatus httpStatus;
	
	
	public ApiResponse(String message, boolean success, HttpStatus httpStatus) {
		super();
		this.message = message;
		this.success = success;
		this.httpStatus = httpStatus;
	}
	
	
	
	public ApiResponse() {
		super();
	}



	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	
}

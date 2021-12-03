package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidAuthorizationToken extends RuntimeException{

	private String message;
	
	public InvalidAuthorizationToken(String message) {
		super();
		this.message = message;
	}
	
	public InvalidAuthorizationToken() {
		this.message = " ";
	}
	
	@Override
	public String toString() {
		return "Invalid Authorization Token: " + this.message;
	}
}

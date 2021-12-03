package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidAdvertiseIdException extends RuntimeException {

private String message;
	
	public InvalidAdvertiseIdException(String message) {
		super();
		this.message = message;
	}
	
	public InvalidAdvertiseIdException() {
		this.message = " ";
	}
	
	@Override
	public String toString() {
		return "Oooppss!!!! Invalid Advertisement Id: " + this.message;
	}
}

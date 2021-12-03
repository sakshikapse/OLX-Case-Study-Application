package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {
	
	private String msg;
   
	@Override
	public String toString() {
		return "No Record Found " + this.msg;
	}

	public RecordNotFoundException(String msg) {
		super();
		this.msg = msg;
	}

	public RecordNotFoundException() {
		this.msg = " ";
	}
	
}




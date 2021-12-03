package com.olx.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(value = { InvalidAuthorizationToken.class })
	public ResponseEntity<Object> handleInvaliduser(RuntimeException exception, WebRequest request) {
        String bodyOfResponse = exception.toString();
		return handleExceptionInternal(exception, 
				bodyOfResponse,
				new HttpHeaders(),
				HttpStatus.UNAUTHORIZED, 
				request);
	}
	
	
	@ExceptionHandler(value = { InvalidAdvertiseIdException.class })
	public ResponseEntity<Object> handleInvalidAdvertiseId(RuntimeException exception, WebRequest request) {
        String bodyOfResponse = exception.toString();
		return handleExceptionInternal(exception, 
				bodyOfResponse,
				new HttpHeaders(),
				HttpStatus.NOT_FOUND, 
				request);
	}

}

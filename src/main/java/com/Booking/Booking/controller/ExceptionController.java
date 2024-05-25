package com.Booking.Booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> handleMyBusinessException() {
		return new ResponseEntity<String>("OK", HttpStatus.FORBIDDEN);
	}
}

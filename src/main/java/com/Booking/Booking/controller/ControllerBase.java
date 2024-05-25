package com.Booking.Booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class ControllerBase {
	protected ResponseEntity<?> ok(Object data) {
		return new ResponseEntity<Object>(data, HttpStatus.OK);
	}
	
	protected ResponseEntity<?> notFound(Object data) {
		return new ResponseEntity<Object>(data, HttpStatus.NOT_FOUND);
	}
	
	protected ResponseEntity<?> badRequest(Object data) {
		return new ResponseEntity<Object>(data, HttpStatus.BAD_REQUEST);
	}

	protected ResponseEntity<?> created(Object data) {
		return new ResponseEntity<Object>(data, HttpStatus.CREATED);
	}
}

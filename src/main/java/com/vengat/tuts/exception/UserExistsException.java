package com.vengat.tuts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6921700581792693869L;

	public UserExistsException() {
		super();
	}

	public UserExistsException(String message) {
		super(message);
	}

	public UserExistsException(String message, Throwable cause) {
		super(message, cause);
	}

}

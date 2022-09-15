package com.vengat.tuts.advice;

import java.util.Date;

import com.vengat.tuts.exception.TokenRefreshException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * This advice class preempts to try catch this exception in controller methods
 * and does away with boiler plate code. The controller could just call the
 * service without the try catch block and return the response with the positive
 * scenario.
 * 
 * @author vengatramanan
 *
 */
@RestControllerAdvice
public class TokenControllerAdvice {

	@ExceptionHandler(value = TokenRefreshException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorMessage handleTokenRefreshException(TokenRefreshException e, WebRequest request) {

		return new ErrorMessage(HttpStatus.FORBIDDEN.value(), new Date(), e.getMessage(),
				request.getDescription(false));
	}

}

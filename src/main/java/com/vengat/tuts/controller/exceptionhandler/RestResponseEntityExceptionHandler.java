package com.vengat.tuts.controller.exceptionhandler;

//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import UserExistsException;
//
///**
// * This class preempts the need for a controller method to handle the exception in a try catch block and handles it globally with a response
// * Similarly TokenControllerAdvice of type ResponseControllerAdvice handles exceptions of a controller methods globally
// * @author vengatramanan
// *
// */
//@ControllerAdvice(basePackages = {"com.vengat.tuts.controller"})
//public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
//	
//	@ExceptionHandler(value = {UserExistsException.class})
//	protected ResponseEntity<Object> handleUserExistsException(RuntimeException ex, HttpHeaders headers, 
//			HttpStatus status, WebRequest request) {
//		
//		return new ResponseEntity<Object>(ex.getMessage(), headers, HttpStatus.CONFLICT);
//		
//	}
//
//}

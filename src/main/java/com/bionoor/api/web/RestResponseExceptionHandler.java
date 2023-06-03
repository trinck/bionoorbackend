package com.bionoor.api.web;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import com.bionoor.api.exceptions.EntityUnknowException;
import com.bionoor.api.exceptions.IllegalOperationException;
import com.bionoor.api.exceptions.ResponseMessageException;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class RestResponseExceptionHandler  extends ResponseEntityExceptionHandler{

   
    
	//IllegalOperationException
    
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex,HttpHeaders headers, HttpStatus status, WebRequest request ){
    	
    	ResponseMessageException exception = new ResponseMessageException();
    	exception.setMessage(ex.getMessage());
    	exception.setDateTime(LocalDateTime.now());
    	exception.setStatus(status.value());
    	
    	return new ResponseEntity<Object>(exception, HttpStatus.NOT_FOUND);
    }
    
    
    
    @ExceptionHandler(value = {IllegalOperationException.class})
    public ResponseEntity<Object> handleEntityNotFound(IllegalOperationException ex,HttpHeaders headers, HttpStatus status, WebRequest request ){
    	
    	ResponseMessageException exception = new ResponseMessageException();
    	exception.setMessage(ex.getMessage());
    	exception.setDateTime(LocalDateTime.now());
    	exception.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    	
    	return new ResponseEntity<Object>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	
    	
	  @ExceptionHandler(value = {EntityUnknowException.class}) public
	  ResponseEntity<Object> handleEntityUnknowException(EntityUnknowException
	  ex, WebRequest request ){
	  
	  ResponseMessageException exception = new ResponseMessageException();
	  Map<String, Object> body = new LinkedHashMap<>();
	  body.put("message",ex.getMessage() );
	  body.put("timestampe",LocalDateTime.now() );
	  body.put("status", HttpStatus.NOT_FOUND );
	 
	  
	  return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND); }
	 
    
	
	  @ExceptionHandler(value = { ConstraintViolationException.class })
	  public ResponseEntity<Object> handleConstraintViolation(
	  ConstraintViolationException ex, WebRequest request) { 
		  
		  ResponseMessageException exception = new ResponseMessageException();
	    	exception.setMessage(ex.getSQLException().getMessage());
	    	exception.setDateTime(LocalDateTime.now());
	    	exception.setStatus(ex.getSQLException().getErrorCode());
	    	
		  return new ResponseEntity<Object>(exception, HttpStatus.CONFLICT);
	  }
	 
	  
	  
	  
	  
	
}

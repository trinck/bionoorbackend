package com.bionoor.api.exceptions;

public class EntityUnknowException extends RuntimeException{

	
	private ResponseMessageException exception;
	public EntityUnknowException(String message) {
		super(message);
		
	}
	
	public EntityUnknowException(ResponseMessageException exception) {
		// TODO Auto-generated constructor stub
		super();
	 this.exception = exception;
		
	}
}

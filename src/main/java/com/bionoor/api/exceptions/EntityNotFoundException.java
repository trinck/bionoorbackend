package com.bionoor.api.exceptions;

import lombok.Data;

@Data
public class EntityNotFoundException  extends RuntimeException{

	
	private ResponseMessageException exception;
	public EntityNotFoundException(String message) {
		super(message);
		
	}
	
	public EntityNotFoundException(ResponseMessageException exception) {
		// TODO Auto-generated constructor stub
		super();
	 this.exception = exception;
		
	}
}

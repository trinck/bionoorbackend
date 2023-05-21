package com.bionoor.api.services;

public interface ServiceInterface <T> {

	
	
	
	public T add(T toSave);
	
	
	public String delete(Long id);
	
	public T modify (T modified);
	
	public T getById(Long id);
}

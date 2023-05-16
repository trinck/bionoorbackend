package com.bionoor.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.models.Category;
import com.bionoor.api.repositories.CategoryRepository;

@Service
public class CategoryService implements ServiceInterface<Category>{
    
  
	CategoryRepository categoryRepository;
	
	public CategoryService(CategoryRepository categoryRepository) {
		// TODO Auto-generated constructor stub
		
		this.categoryRepository = categoryRepository;
	}
	
	
	public Category add(Category category ) {
		
		return this.categoryRepository.save(category);
	}
	
	
	public String delete(Long id) {
			
		
		String message = "";
		
	      Category category = this.categoryRepository.getReferenceById(id);
			if(category == null) {
				message = "Category with id "+id+" doesn't exist";
			}else {
				this.categoryRepository.deleteById(id);
				message = "Category "+id+" has been deleted";
			}
	      
	      return message;
	}
	
	public Category modify(Category category) {
				
			
		  return this.categoryRepository.save(category);
		      
			
	}


	@Override
	public Category getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


}


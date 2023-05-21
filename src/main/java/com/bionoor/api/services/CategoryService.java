package com.bionoor.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.admin.AdminCategory.InputCategory;
import com.bionoor.api.models.Category;
import com.bionoor.api.models.Media;
import com.bionoor.api.repositories.CategoryRepository;
import com.bionoor.api.utils.ServiceStorageIn;

@Service
public class CategoryService{
    
  
	
	CategoryRepository categoryRepository;
	ServiceStorageIn serviceStorageIn;
	MediaService mediaService;
	
	public CategoryService(CategoryRepository categoryRepository, MediaService mediaService, ServiceStorageIn serviceStorageIn) {
		// TODO Auto-generated constructor stub
		
		this.categoryRepository = categoryRepository;
		this.mediaService = mediaService;
		this.serviceStorageIn = serviceStorageIn;
	}
	
	
	public Category add(InputCategory inCategory ) {
		
		Category category = new Category();
	    Media media =	this.serviceStorageIn.store(inCategory.getImage(), inCategory.getImage().getOriginalFilename());
		
	    category.setName(inCategory.getName());
	    category.setImage(media.getUrl());
	    if(inCategory.getParent() != null) {
	        Category parent = this.categoryRepository.findById(inCategory.getParent()).get();
	        category.setParentCategory(parent);
	    }
	   
	    
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
	
	
	public List<Category> allCategory() {
		
		
		  return this.categoryRepository.findAll();
		      
			
	}


	public Category getById(Long id) {
		
		return  this.categoryRepository.findById(id).orElse(null);
	}


}


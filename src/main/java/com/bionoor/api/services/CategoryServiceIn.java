package com.bionoor.api.services;

import java.util.List;

import com.bionoor.api.admin.AdminCategory.InputCategory;
import com.bionoor.api.models.Category;
import com.bionoor.api.models.Media;
import com.bionoor.api.repositories.CategoryRepository;
import com.bionoor.api.utils.ServiceStorageIn;

public interface CategoryServiceIn {

	
	public Category add(InputCategory inCategory ) ;
	public Category add(Category Category ) ;
	
	
	public Category delete(Long id) ;
	
	public Category modify(Category category) ;
	
	public List<Category> allCategories();


	public Category getById(Long id) ;

	
}

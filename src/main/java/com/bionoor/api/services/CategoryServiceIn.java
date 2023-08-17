package com.bionoor.api.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.bionoor.api.admin.AdminCategory.InputCategory;
import com.bionoor.api.models.Category;
import com.bionoor.api.models.Media;
import com.bionoor.api.models.Order;
import com.bionoor.api.repositories.CategoryRepository;
import com.bionoor.api.utils.ServiceStorageIn;

public interface CategoryServiceIn {

	
	public Category add(InputCategory inCategory ) ;
	public Category add(Category Category ) ;
	
	
	public Category delete(Long id) ;
	
	public Category modify(Category category) ;
	
	public List<Category> allCategories();


	public Category getById(Long id) ;
	public Map<String, Object> getDataGraphs(Long id);
	
	public Page<Category> findById(int page, int size, Long id, String sort);
 	public Page<Category> findByName( int page, int size, String sort,String name);
	public List<Category> findByNames(List<String> categories);
 	
    

	
}

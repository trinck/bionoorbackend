package com.bionoor.api.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bionoor.api.admin.AdminCategory.InputCategory;
import com.bionoor.api.models.Category;
import com.bionoor.api.models.Media;
import com.bionoor.api.models.Order;
import com.bionoor.api.models.Product;
import com.bionoor.api.repositories.CategoryRepository;
import com.bionoor.api.repositories.ProductRepository;
import com.bionoor.api.utils.ServiceStorageIn;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Service
public class CategoryService implements CategoryServiceIn{
    
  
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ServiceStorageIn serviceStorageIn;
	@Autowired
	private MediaService mediaService;
	
	@Autowired
	private ProductRepository productRepository;
	
	
	
	
	
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
	
	
	public Category delete(Long id) {
			
	      Category category = this.categoryRepository.getReferenceById(id);
			if(category == null) {
				throw new EntityNotFoundException("Category with id "+id+" doesn't exist");
			}else {
				
				//ExecutorService executor = Executors.newFixedThreadPool(10);
				    
					for(Category child : category.getSubCategories()) {
						child.setParentCategory(null);
						this.categoryRepository.save(child);
					}
	
				
				
				  for(Product product : category.getProducts()) {
					  product.setCategory(null);
					  this.productRepository.save(product); 
					  }
				 
				this.categoryRepository.deleteById(id);;
				
			}
	      
	      return category;
	}
	
	
	
	public Category modify(Category category) {
				
		  return this.categoryRepository.save(category);
		      	
	}
	
	
	public List<Category> allCategories() {
		
		
		  return this.categoryRepository.findAll();
		      	
	}


	public Category getById(Long id) {
		
		Category category = this.categoryRepository.findById(id).orElse(null);
		if(category!=null) {
			return category;
		}
		
		throw new EntityNotFoundException("Entity Category with id = "+id+" did not found");
	}


	@Override
	public Category add(Category Category) {
		
		return this.categoryRepository.save(Category);
	}

 
	public Map<String, Object> getDataGraphs(Long id){
		
		List<Product>  products = this.productRepository.findAll();
		
		Category category = this.getById(id);
		Map<String, Object> data = Map.of("products", products, "category", category);
		
		return data;
	}


	@Override
	public Page<Category> findById(int page, int size, Long id, String sort) {
		String [] sorts = sort.split(":");
		Page<Category> pages;
		if(id != null) {
			if(sorts[1].equalsIgnoreCase("ascending")) {
				pages = this.categoryRepository.findById(id, PageRequest.of(page, size, Sort.by(sorts[0]).ascending()));

			}else {
				
				pages = this.categoryRepository.findById(id, PageRequest.of(page, size, Sort.by(sorts[0]).descending()));

			}
		}else {
			
			
			if(sorts[1].equalsIgnoreCase("ascending")) {
				pages = this.categoryRepository.findAll( PageRequest.of(page, size, Sort.by(sorts[0]).ascending()));
			}else {
				
				pages = this.categoryRepository.findAll( PageRequest.of(page, size, Sort.by(sorts[0]).descending()));

			}
			

		}
		
				
		 return	pages;
	}


	@Override
	public Page<Category> findByName(int page, int size, String sort, String name) {
		String [] sorts = sort.split(":");
		Page<Category> pages;
		if(name != null) {
			if(sorts[1].equalsIgnoreCase("ascending")) {
				pages = this.categoryRepository.findByNameContains(name, PageRequest.of(page, size, Sort.by(sorts[0]).ascending()));

			}else {
				
				pages = this.categoryRepository.findByNameContains(name, PageRequest.of(page, size, Sort.by(sorts[0]).descending()));

			}
		}else {
			
			
			if(sorts[1].equalsIgnoreCase("ascending")) {
				pages = this.categoryRepository.findAll( PageRequest.of(page, size, Sort.by(sorts[0]).ascending()));
			}else {
				
				pages = this.categoryRepository.findAll( PageRequest.of(page, size, Sort.by(sorts[0]).descending()));

			}
			

		}
		
				
		 return	pages;
	}



	@Override
	public List<Category> findByNames(List<String> categories) {
		// TODO Auto-generated method stub
		return this.categoryRepository.findByNames(categories);
	}


	
}


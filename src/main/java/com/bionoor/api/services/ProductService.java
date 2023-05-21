package com.bionoor.api.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.models.Product;
import com.bionoor.api.repositories.ProductRepository;

@Service
public class ProductService {

	
			@Autowired
	private ProductRepository productRepository;
	
	
	
	
	public Product add(Product product ) {
			
			return this.productRepository.save(product);
		}
	
	
	public String delete(Long id) {
			
		
		String message = "";
		
	      Product product = this.productRepository.getReferenceById(id);
			if(product == null) {
				message = "Product with id "+id+" doesn't exist";
			}else {
				this.productRepository.deleteById(id);
				message = "Product "+id+" has been deleted";
			}
	      
	      return message;
	}
	
	public Product modify(Product product) {
				
			
		 return	this.productRepository.save(product);
		      
			
	}




	public Product getById(Long id) {
		// TODO Auto-generated method stub
		return this.productRepository.findById(id).orElse(null);
	}
	
	
}

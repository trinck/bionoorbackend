package com.bionoor.api.services;


import com.bionoor.api.models.Product;
import com.bionoor.api.repositories.ProductRepository;

public class ProductService implements ServiceInterface<Product>{

	
	
	ProductRepository productRepository;
	
	public ProductService( ProductRepository productRepository) {
		// TODO Auto-generated constructor stub
		this.productRepository = productRepository;
	}
	
	
	
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



	@Override
	public Product getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

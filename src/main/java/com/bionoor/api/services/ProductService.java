package com.bionoor.api.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.admin.AdminProduct.InputProductDto;
import com.bionoor.api.models.Category;
import com.bionoor.api.models.Product;
import com.bionoor.api.repositories.ProductRepository;
import com.bionoor.api.utils.*;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService implements  ProductServiceIn{

	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ServiceStorageImpl ServiceStorageImpl;
	
	
	
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
		Product product = this.productRepository.findById(id).orElse(null);
		
		if(product!=null) {
			return product;
		}
		
		throw new EntityNotFoundException("Entity product id= "+id+" did not found");
	}


	@Override
	public Product add(InputProductDto toSave) {

		Product product = new Product(toSave);
		Category categpry = this.categoryService.getById(toSave.getCategory());
		product.setCategory(categpry);
		product.setImages( this.ServiceStorageImpl.storeAll(toSave.getImages()));
		
		categpry.getProducts().add(product);
		//this.categoryService.add(categpry);
		
		
		return this.productRepository.save(product);
	}


	@Override
	public List<Product> allProducts() {
		// TODO Auto-generated method stub
		return this.productRepository.findAll();
	}
	
	
}

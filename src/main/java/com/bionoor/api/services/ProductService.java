package com.bionoor.api.services;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bionoor.api.admin.AdminProduct.InputProductDto;
import com.bionoor.api.models.Category;
import com.bionoor.api.models.Order;
import com.bionoor.api.models.Payment;
import com.bionoor.api.models.Product;
import com.bionoor.api.repositories.ProductRepository;
import com.bionoor.api.utils.*;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;

@Service
@Data
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
		product.setCreateAt(new Date());
		
		categpry.getProducts().add(product);
		//this.categoryService.add(categpry);
		
		
		return this.productRepository.save(product);
	}


	@Override
	public List<Product> allProducts() {
		// TODO Auto-generated method stub
		return this.productRepository.findAll();
	}
	
	public Product findByName(String productName) {
		
		List<Product>  products = this.productRepository.findByName(productName);
		
		if(products.size()>0) {
			
			return products.get(0);
		}
		
		throw new EntityNotFoundException("product with name = "+productName+" doesn't exist");
	}


	@Override
	public Product findByCode(String code) {
		// TODO Auto-generated method stub
		return this.productRepository.findByCode(code);
	}


	@Override
	public Page<Product> pagesById(int page, int size, Long id, String sort) {
		String [] sorts = sort.split(":");
		Page<Product> pages;
		if(id != null) {
			if(sorts[1].equalsIgnoreCase("ascending")) {
				pages = this.productRepository.findById(id, PageRequest.of(page, size, Sort.by(sorts[0]).ascending()));

			}else {
				
				pages = this.productRepository.findById(id, PageRequest.of(page, size, Sort.by(sorts[0]).descending()));

			}
		}else {
			
			
			if(sorts[1].equalsIgnoreCase("ascending")) {
				pages = this.productRepository.findAll( PageRequest.of(page, size, Sort.by(sorts[0]).ascending()));
			}else {
				
				pages = this.productRepository.findAll( PageRequest.of(page, size, Sort.by(sorts[0]).descending()));

			}
			

		}
		
				
		 return	pages;
	}


	@Override
	public Page<Product> pagesByCode(int page, int size, String sort, String code) {
		String [] sorts = sort.split(":");
		Page<Product> pages;
		if(code != null) {
			if(sorts[1].equalsIgnoreCase("ascending")) {
				pages = this.productRepository.findByCodeContains(code, PageRequest.of(page, size, Sort.by(sorts[0]).ascending()));

			}else {
				
				pages = this.productRepository.findByCodeContains(code, PageRequest.of(page, size, Sort.by(sorts[0]).descending()));

			}
		}else {
			
			
			if(sorts[1].equalsIgnoreCase("ascending")) {
				pages = this.productRepository.findAll( PageRequest.of(page, size, Sort.by(sorts[0]).ascending()));
			}else {
				
				pages = this.productRepository.findAll( PageRequest.of(page, size, Sort.by(sorts[0]).descending()));

			}
			

		}
		
				
		 return	pages;
	}


	@Override
	public Page<Product> pagesByName(int page, int size, String sort, String name) {
		String [] sorts = sort.split(":");
		Page<Product> pages;
		if(name != null) {
			if(sorts[1].equalsIgnoreCase("ascending")) {
				pages = this.productRepository.findByNameContains(name, PageRequest.of(page, size, Sort.by(sorts[0]).ascending()));

			}else {
				
				pages = this.productRepository.findByNameContains(name, PageRequest.of(page, size, Sort.by(sorts[0]).descending()));

			}
		}else {
			
			
			if(sorts[1].equalsIgnoreCase("ascending")) {
				pages = this.productRepository.findAll( PageRequest.of(page, size, Sort.by(sorts[0]).ascending()));
			}else {
				
				pages = this.productRepository.findAll( PageRequest.of(page, size, Sort.by(sorts[0]).descending()));

			}
			

		}
		
				
		 return	pages;
	}


	@Override
	public List<Product> findByNames(List<String> products) {
		// TODO Auto-generated method stub
		return this.productRepository.findByNames(products);
	}
	
	
}

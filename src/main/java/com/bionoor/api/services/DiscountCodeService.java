package com.bionoor.api.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.dto.InputDiscountCustomerDTO;
import com.bionoor.api.exceptions.FieldsAlreadyExistsException;
import com.bionoor.api.models.Category;
import com.bionoor.api.models.Customer;
import com.bionoor.api.models.DiscountCode;
import com.bionoor.api.models.DiscountDCC;
import com.bionoor.api.models.DiscountDCP;
import com.bionoor.api.models.Product;
import com.bionoor.api.repositories.DiscountCodeRepository;
import com.bionoor.api.web.RestDiscount.InputDiscountCategory;
import com.bionoor.api.web.RestDiscount.InputDiscountProduct;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DiscountCodeService{

	@Autowired
	private DiscountCodeRepository codeRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CustomerService customerService;
	
	public DiscountCode add(DiscountCode toSave) {
		
		return this.codeRepository.save(toSave);
	}
	
	public List<DiscountCode> all() {
		
		return this.codeRepository.findAll();
	}
	
	
	public DiscountCode toggleActif(Long discountId, Boolean toggle) {
		
		DiscountCode code = this.codeRepository.findById(discountId).orElse(null);
		if(code!=null) {
			code.setActif(toggle);
		   return	this.codeRepository.save(code);
		}else {
			throw new EntityNotFoundException("Discount with id= "+discountId+" did not found");
		}
		
		
		
	}
	

	//delete discount code	
	public DiscountCode delete(Long id) {
		
		DiscountCode discountCode = this.getById(id);
		discountCode.getDiscountables().forEach(discountable ->{
			//delete discount code from all discountable
			discountable.getDiscountCodes().remove(discountCode);
			
		});
		
		discountCode.getUsedBy().forEach(customer ->{
			customer.getUsedDiscountCodes().remove(discountCode);
		});
		
		if(discountCode.getDiscriminatorValue().equalsIgnoreCase("DCC")) {
			((DiscountDCC)discountCode ).getCustomer().getDiscountDCCs().remove(discountCode);
		}else {
			
			this.codeRepository.deleteById(id);
		}
		
		return discountCode;
	}

	
	
	
	public DiscountCode modify(DiscountCode modified) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public DiscountCode addInput(InputDiscountProduct inputDiscount)  {
		
		
		  DiscountCode discountCode = new DiscountDCP(inputDiscount); 
		  Product product = this.productService.getById(inputDiscount.getProductId());
		  
		  product.getDiscountCodes().add(discountCode);
		  discountCode.getDiscountables().add(product);
		  discountCode.setActif(true);
		  
		  discountCode = this.codeRepository.save(discountCode);
		 
		//return discountCode;
		return discountCode;
	}
	
	
	
	
	
	public DiscountCode addInput(InputDiscountCustomerDTO inputDiscount)  {
		
		
		 final DiscountCode discountCode = new DiscountDCC(inputDiscount); 
		  
		  List<Product> products = new ArrayList<>();
		  List<Category> categories = new ArrayList<>();
		  Customer customer = this.customerService.getCustomerById(inputDiscount.getCustomerId());
		  
			 
		  products = this.productService.getProductRepository().findByNames(inputDiscount.getProducts());	 
		  categories =this.categoryService.getCategoryRepository().findByNames(inputDiscount.getCategories());
		  
		  
		//  product.getDiscountCodes().add(discountCode);
		 // discountCode.getDiscountables().add(product);
		 // discountCode.setActif(true);
		  
		//  
		 
		 discountCode.getDiscountables().addAll(products);
		 discountCode.getDiscountables().addAll(categories);
		 
		 discountCode.getDiscountables().forEach(d ->{
			 d.getDiscountCodes().add(discountCode);
		 });
		 
		 ((DiscountDCC)discountCode).setCustomer(customer);
		 DiscountCode savedDiscount = this.codeRepository.save(discountCode);	 
		 customer.getDiscountDCCs().add((DiscountDCC) savedDiscount); 
		 this.customerService.createCustomer(customer);
		 
		return savedDiscount;
	}
	
	
	
	
	
	
	
	public DiscountCode addInput(InputDiscountCategory inputDiscount) {
			
		
		DiscountDCP discountCode = new DiscountDCP(inputDiscount); 
		  
		  Category category = this.categoryService.getById(inputDiscount.getCategoryId());
		  discountCode.getDiscountables().add(category);
		  category.getDiscountCodes().add(discountCode);
		  discountCode.setActif(true);
		  discountCode.setReusable(true);
		  discountCode.setCreatedAt(new Date());
		  discountCode = this.codeRepository.save(discountCode);
		 
			//return  discountCode; //
		
		return discountCode;
		}


	public DiscountCode getById(Long id) {
		
	  DiscountCode code =	this.codeRepository.findById(id).orElse(null);
	  
	  if(code!=null) {
		  return code;
	  }
		 throw new EntityNotFoundException("Discount code with id= "+id+" did not found");
	  
	}
	
	
	public DiscountCode getByCode(String code) {
		
		 List<DiscountCode>  codes =	this.codeRepository.findByCode(code);
		  
		  if(codes.size()>0) {
			  return codes.get(0);
		  }
			 throw new EntityNotFoundException("Discount code with name= "+code+" did not found");
		  
		}
   
}

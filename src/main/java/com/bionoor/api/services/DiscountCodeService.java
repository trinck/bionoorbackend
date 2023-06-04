package com.bionoor.api.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.exceptions.FieldsAlreadyExistsException;
import com.bionoor.api.models.Category;
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
	
	public DiscountCode add(DiscountCode toSave) {
		
		return this.codeRepository.save(toSave);
	}
	
	public List<DiscountCode> all() {
		
		return this.codeRepository.findAll();
	}
	
	
	public void toggleActif(Long discountId, Boolean toggle) {
		
		DiscountCode code = this.codeRepository.findById(discountId).orElse(null);
		if(code!=null) {
			code.setActif(toggle);
			this.codeRepository.save(code);
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
		
		if(discountCode instanceof DiscountDCC) {
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
	
	public DiscountCode addInput(InputDiscountProduct inputDiscount) throws SQLIntegrityConstraintViolationException {
		
		
		  DiscountCode discountCode = new DiscountDCP(inputDiscount); 
		  Product product = this.productService.getById(inputDiscount.getProductId());
		  
		  product.getDiscountCodes().add(discountCode);
		  discountCode.getDiscountables().add(product);
		  discountCode.setActif(true);
		  
		  discountCode = this.codeRepository.save(discountCode);
		 
		//return discountCode;
		return discountCode;
	}
	
	
	public DiscountCode addInput(InputDiscountCategory inputDiscount) {
			
		
		  DiscountCode discountCode = new DiscountDCP(inputDiscount); 
		  
		  Category category = this.categoryService.getById(inputDiscount.getCategoryId());
		  discountCode.getDiscountables().add(category);
		  category.getDiscountCodes().add(discountCode);
		  discountCode.setActif(true);
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
		  
		  if(code.length()>0) {
			  return codes.get(0);
		  }
			 throw new EntityNotFoundException("Discount code with name= "+code+" did not found");
		  
		}
   
}

package com.bionoor.api.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.exceptions.FieldsAlreadyExistsException;
import com.bionoor.api.models.Category;
import com.bionoor.api.models.DiscountCode;
import com.bionoor.api.models.DiscountProduct;
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
		
		return  null;// this.codeRepository.save(toSave);
	}
	
	public List<DiscountCode> all() {
		
		return null;//this.codeRepository.findAll();
	}
	
	
	public void toggleActif(Long discountId, Boolean toggle) {
		
		/*
		 * DiscountCode code = this.codeRepository.findById(discountId).orElse(null);
		 * if(code!=null) { code.setActif(toggle); this.codeRepository.save(code); }else
		 * { throw new
		 * EntityNotFoundException("Discount with id= "+discountId+" did not found"); }
		 */
		
		
	}
	

	
	public String delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public DiscountCode modify(DiscountCode modified) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public DiscountCode addInput(InputDiscountProduct inputDiscount) throws SQLIntegrityConstraintViolationException {
		
		//DiscountCode discountCode = new DiscountProduct(inputDiscount);
		Product product = this.productService.getById(inputDiscount.getProductId());
		
		
		//product.setDiscountCodes(discountCode);
		//discountCode.setProduct(product);
	//	discountCode.setActif(true);
		
		//discountCode = this.codeRepository.save(discountCode);
		
		return null;
	}
	
	
public DiscountCode addInput(InputDiscountCategory inputDiscount) {
		
		//DiscountCode discountCode = new DiscountProduct(inputDiscount);
		Category category = this.categoryService.getById(inputDiscount.getCategoryId());
		/*
		 * discountCode.setCategory(category); category.setDiscountCode(discountCode);
		 */
		/*
		 * discountCode.setActif(true);
		 * 
		 * discountCode = this.codeRepository.save(discountCode);
		 */
		return  null; //
	}


	public DiscountCode getById(Long id) {
		
		/*
		 * DiscountCode code = this.codeRepository.findById(id).orElse(null);
		 * 
		 * if(code!=null) { return code; } throw new
		 * EntityNotFoundException("Discount with id= "+id+" did not found");
		 */
		return null;
	}
   
}

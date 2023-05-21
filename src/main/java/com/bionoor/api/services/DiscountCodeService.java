package com.bionoor.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.models.Category;
import com.bionoor.api.models.DiscountCode;
import com.bionoor.api.models.Product;
import com.bionoor.api.repositories.DiscountCodeRepository;
import com.bionoor.api.web.RestDiscount.InputDiscountCategory;
import com.bionoor.api.web.RestDiscount.InputDiscountProduct;

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
	

	
	public String delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public DiscountCode modify(DiscountCode modified) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public DiscountCode addInput(InputDiscountProduct inputDiscount) {
		
		DiscountCode discountCode = new DiscountCode();
		Product product = this.productService.getById(inputDiscount.getProductId());
		discountCode.setCode(inputDiscount.getCode());
		discountCode.setEndDate(inputDiscount.getEndDate());
		discountCode.setPercentage(inputDiscount.getPercentage());
		discountCode.setProduct(product);
		
		
		return this.codeRepository.save(discountCode);
	}
	
	
public DiscountCode addInput(InputDiscountCategory inputDiscount) {
		
		DiscountCode discountCode = new DiscountCode();
		Category category = this.categoryService.getById(inputDiscount.getCategoryId());
		discountCode.setCode(inputDiscount.getCode());
		discountCode.setEndDate(inputDiscount.getEndDate());
		discountCode.setPercentage(inputDiscount.getPercentage());
		discountCode.setCategory(category);
		
		discountCode = this.codeRepository.save(discountCode);
		category.setDiscountCode(discountCode);
		
		this.categoryService.modify(category);
		return  discountCode; //
	}


	public DiscountCode getById(Long id) {
		// TODO Auto-generated method stub
		return this.codeRepository.findById(id).orElse(null);
	}
   
}

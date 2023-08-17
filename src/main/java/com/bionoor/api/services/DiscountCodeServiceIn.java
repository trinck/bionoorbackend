package com.bionoor.api.services;


import com.bionoor.api.dto.InputDiscountCustomerDTO;
import com.bionoor.api.models.DiscountCode;
import com.bionoor.api.web.RestDiscount.InputDiscountCategory;
import com.bionoor.api.web.RestDiscount.InputDiscountProduct;

public interface DiscountCodeServiceIn {

	
	
	
public DiscountCode add(DiscountCode toSave) ;
	
	
	public DiscountCode toggleActif(Long discountId, Boolean toggle);
	//delete discount code	
	public DiscountCode delete(Long id) ;
	
	
	public DiscountCode modify(DiscountCode modified) ;
	public DiscountCode addInput(InputDiscountProduct inputDiscount)  ;
	
	
	
	
	
	public DiscountCode addInput(InputDiscountCustomerDTO inputDiscount)  ;
	
	
	
	
	
	
	public DiscountCode addInput(InputDiscountCategory inputDiscount);

	public DiscountCode getById(Long id) ;
	
	public DiscountCode getByCode(String code) ;
}

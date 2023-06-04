package com.bionoor.api.dto;

import com.bionoor.api.models.DiscountCode;
import com.bionoor.api.models.DiscountDCC;
import com.bionoor.api.models.DiscountDCP;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OutputDiscounDCP extends OutputDiscountCodeDTO{

	
	private Boolean reusable;
	
	public OutputDiscounDCP(DiscountCode discountCode) {
		
		super(discountCode);
		if( discountCode instanceof DiscountDCP) {
			
			this.reusable = ((DiscountDCP) discountCode).getReusable();
		}else {
			throw new IllegalArgumentException("instance is not of DiscountDCP");
		}
	}
}

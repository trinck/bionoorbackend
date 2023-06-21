package com.bionoor.api.dto;

import com.bionoor.api.models.DiscountCode;
import com.bionoor.api.models.DiscountDCC;
import com.bionoor.api.models.DiscountDCC.Type;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OutputDiscountDCC extends OutputDiscountCodeDTO{

	private Type type;
	private boolean isUnique;
	
	public OutputDiscountDCC(DiscountCode discountCode) {
		
		super(discountCode);
		if( discountCode instanceof DiscountDCC) {
			
			this.isUnique = ((DiscountDCC) discountCode).getIsUnique();
			  this.type = ((DiscountDCC) discountCode).getType();
		}else {
			throw new IllegalArgumentException("instance is not of DiscountDCC");
		}
	}
}

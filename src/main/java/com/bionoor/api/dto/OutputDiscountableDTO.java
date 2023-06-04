package com.bionoor.api.dto;

import java.util.ArrayList;
import java.util.List;

import com.bionoor.api.models.DiscountCode;
import com.bionoor.api.models.Discountable;
import com.bionoor.api.models.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javassist.expr.Instanceof;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class OutputDiscountableDTO {

	
	
	private Long id;
	private String discrimatorValue;
	
	
	public OutputDiscountableDTO( Discountable discountable) {
		this.id = discountable.getId();
		
		if(discountable instanceof  Product) {
			this.discrimatorValue = "Product";
		}else {
			this.discrimatorValue = "Category";
		}
		
		
		
	}
}

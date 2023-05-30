package com.bionoor.api.models;

import java.io.Serializable;

import com.bionoor.api.web.RestDiscount.InputDiscountIn;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@DiscriminatorValue(value = "DCP")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DiscountDCP extends DiscountCode implements Serializable{
	
	private Boolean reusable;
	
	 public DiscountDCP(InputDiscountIn discountCategory) {
	    	
	    	super(discountCategory);
	    	
	    }
}

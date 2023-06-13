package com.bionoor.api.models;

import java.io.Serializable;

import com.bionoor.api.web.RestDiscount.InputDiscountIn;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@DiscriminatorValue(value = "DCC")
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDCC extends DiscountCode implements Serializable{

	
	public enum Type{
		AMOUNT,PERCENTAGE;
	}
	
	private Boolean isUnique;
	
	@Enumerated(EnumType.STRING)
	private Type type;
	@ManyToOne
	@JoinColumn(name = "cusromer_id")
	private Customer customer;
	
	
	public DiscountDCC(InputDiscountIn discountCategory) {
    	
    	super(discountCategory);
    	
    }
}

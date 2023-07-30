package com.bionoor.api.models;

import java.io.Serializable;

import com.bionoor.api.dto.InputDiscountCustomerDTO;
import com.bionoor.api.dto.InputDiscountIn;

import jakarta.persistence.CascadeType;
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
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "cusromer_id")
	private Customer customer;
	
	
	public DiscountDCC(InputDiscountIn discountCode) {
    	
    	super(discountCode);
    	InputDiscountCustomerDTO discountCustomerDTO = (InputDiscountCustomerDTO) discountCode;
    	
    	this.isUnique = discountCustomerDTO.getUnique();
    	
    	switch(discountCustomerDTO.getType()) {
    	
    		case "amount": this.type = Type.AMOUNT;break;
    		default: this.type =Type.PERCENTAGE; break;
    	}
    	
    }
}

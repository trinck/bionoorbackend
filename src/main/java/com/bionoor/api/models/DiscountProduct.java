package com.bionoor.api.models;

import java.io.Serializable;

import com.bionoor.api.web.RestDiscount.InputDiscountCategory;
import com.bionoor.api.web.RestDiscount.InputDiscountProduct;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@DiscriminatorValue(value = "cp")
@Data
@NoArgsConstructor
//@Table(name = "discount_code_prod/cat")
public class DiscountProduct  implements Serializable{

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private Boolean reusable; 
	
	public DiscountProduct(InputDiscountCategory category) {
		
	}
	
	public DiscountProduct(InputDiscountProduct discountProduct) {
		
	}
}

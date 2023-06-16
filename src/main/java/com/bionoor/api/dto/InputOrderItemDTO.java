package com.bionoor.api.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InputOrderItemDTO{
	 
		private Long order;
		private Long id;
		@Min(value = 1 )
	    private int quantity; // quantity of the product in the order
		@NotEmpty
	    private String productName; // product that the order item refers to
	    
}


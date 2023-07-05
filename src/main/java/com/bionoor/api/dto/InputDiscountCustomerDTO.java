package com.bionoor.api.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import com.bionoor.api.models.Category;
import com.bionoor.api.models.Product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InputDiscountCustomerDTO implements InputDiscountIn{

	
		@NotNull
		private UUID customerId;
		
		@NotNull
		private Boolean unique;
		@NotNull
		private Boolean actif;
		
	   @NotBlank @NotEmpty
		private String code;
	   @NotBlank @NotEmpty
	    private String type;
	    private List<String> products;// the products which are affected by reduce
	    private List<String> categories;
	    
	    @NotNull @Min(value = 1)
	    private Double discount;

	    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	    @NotNull
	    private Date endDate;

	
	
	
	
}

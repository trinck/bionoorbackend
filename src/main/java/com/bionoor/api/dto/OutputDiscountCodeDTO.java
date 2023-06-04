package com.bionoor.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.bionoor.api.models.Customer;
import com.bionoor.api.models.DiscountCode;
import com.bionoor.api.models.Discountable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OutputDiscountCodeDTO {

	
	   protected Long id;

	   @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	   protected Date createdAt;
	    
	    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	    protected Date modifiedAt;
	    
	  
	    protected String code;
	    
	    private String discriminatorValue;

	    protected List<OutputDiscountableDTO> discountables = new ArrayList<>();
	    
	   
	    protected Double discount;
	    
	    
	    protected Boolean actif;

	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    protected Date endDate;
	    
	public OutputDiscountCodeDTO(DiscountCode discountCode) {
		
		this.id = discountCode.getId();
		this.createdAt = discountCode.getCreatedAt();
		this.actif = discountCode.getActif();
		this.code = discountCode.getCode();
		this.modifiedAt = discountCode.getModifiedAt();
		this.discriminatorValue = discountCode.getDiscriminatorValue();
		this.endDate = discountCode.getEndDate();
		this.discount = discountCode.getDiscount();
		discountCode.getDiscountables().forEach(dicountable ->{
			this.discountables.add(new OutputDiscountableDTO(dicountable));
		});
		
	}
	
}

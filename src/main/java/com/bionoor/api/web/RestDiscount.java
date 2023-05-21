package com.bionoor.api.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bionoor.api.models.Category;
import com.bionoor.api.models.DiscountCode;
import com.bionoor.api.models.Product;
import com.bionoor.api.services.DiscountCodeService;

import ch.qos.logback.core.status.Status;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;


@RestController
@CrossOrigin("*")
public class RestDiscount {

	
	@Autowired
	DiscountCodeService discountCodeService;
	
	
	
	
	@Value("${app.name}")
	private String name;
	@Value("${app.logo}")
	 private String logo;
	
	
	

	@GetMapping(value = "/api/discounts/all")
	public List<DiscountCode> discountCodes() {
		 List<DiscountCode> discountCodes = this.discountCodeService.all();
		return discountCodes;
	}
	
	@ResponseBody
	@GetMapping(value = "/api/discounts/test")
	public String test() {
		
		return "test";
	}
	
	
	@GetMapping(value = "/api/discounts/index/{id}")
	public DiscountCode discount( @PathVariable(value = "id") Long id) {
		
		DiscountCode discountCode = this.discountCodeService.getById(id);
		
		
		return discountCode;
	}
	
	
	@PostMapping(value = "/api/discounts/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
	public DiscountCode  addDiscount(@ModelAttribute InputDiscountCategory inputDiscount) {
		
		return  this.discountCodeService.addInput(inputDiscount); 
		
	}
	
	
	//class pojo***************************************************************************************
	@Data
	@NoArgsConstructor
	public class InputDiscountProduct {
		 
		    private String code;
		    
		    private Long productId;// the product which is affected by reduce
		    
		    private int percentage;

		    @DateTimeFormat(pattern = "yyyy-MM-dd")
		    private Date endDate;

	}
	
	
	@Data
	@NoArgsConstructor
	public class InputDiscountCategory {
		 
		    private String code;		  
		    private Long categoryId;
		    private int percentage;

		    @DateTimeFormat(pattern = "yyyy-MM-dd")
		    private Date endDate;

	}
	
}

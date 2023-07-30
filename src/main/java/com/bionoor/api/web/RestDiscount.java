package com.bionoor.api.web;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bionoor.api.dto.InputDiscountCustomerDTO;
import com.bionoor.api.dto.InputDiscountIn;
import com.bionoor.api.dto.OutputDiscounDCP;
import com.bionoor.api.dto.OutputDiscountCodeDTO;
import com.bionoor.api.dto.OutputDiscountDCC;
import com.bionoor.api.exceptions.EntityUnknowException;
import com.bionoor.api.exceptions.FieldsAlreadyExistsException;
import com.bionoor.api.models.Category;
import com.bionoor.api.models.DiscountCode;
import com.bionoor.api.models.DiscountDCC;
import com.bionoor.api.models.Product;
import com.bionoor.api.services.DiscountCodeService;

import ch.qos.logback.core.status.Status;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/discounts")
public class RestDiscount {

	
	@Autowired
	DiscountCodeService discountCodeService;
	
	
	
	
	@Value("${app.name}")
	private String name;
	@Value("${app.logo}")
	 private String logo;
	
	
	

	@GetMapping(value = "/all")
	public List<DiscountCode> discountCodes() {
		 List<DiscountCode> discountCodes = this.discountCodeService.all();
		return discountCodes;
	}
	
	
	@PostMapping(value = "/put/code")
	public ResponseEntity<String>  putCode(@Valid @ModelAttribute DistcountDtoCode  distcountDtoCode) {
		
		
		 DiscountCode discountCode = this.discountCodeService.getById(distcountDtoCode.getId());
		 discountCode.setCode(distcountDtoCode.getCode());
		 discountCode = this.discountCodeService.add(discountCode);
		
		return new ResponseEntity<String>(discountCode.getCode(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/put/endDate")
	public OutputDiscountCodeDTO  putEndDate(@Valid @ModelAttribute DistcountDtoEndDate  distcountDtoEndDate) {
		
		
		 DiscountCode discountCode = this.discountCodeService.getById(distcountDtoEndDate.getId());
		 discountCode.setEndDate(distcountDtoEndDate.getEnDate());
		 discountCode = this.discountCodeService.add(discountCode);
		
		return new OutputDiscountCodeDTO(discountCode);
	}
	

	@PostMapping(value = "/toggleActif", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
	public OutputDiscountCodeDTO toggleActif(@ModelAttribute toggleActif toggle) {
		
		  DiscountCode discountCode =  this.discountCodeService.toggleActif(toggle.getDiscountId(), toggle.getToggle());
		return new OutputDiscountCodeDTO(discountCode);
	}
	
	
	@GetMapping(value = "/index/{id}")
	public OutputDiscountCodeDTO discount( @PathVariable(value = "id") Long id) {
		
		DiscountCode discountCode = this.discountCodeService.getById(id);
		
		if( discountCode instanceof DiscountDCC) {
			
			return new OutputDiscountDCC(discountCode);
		}
		
		return new OutputDiscounDCP(discountCode) ;
	}
	
	
	@GetMapping(value = "/find/code")
	public OutputDiscountCodeDTO findByCode( @RequestParam String code) {
		
		DiscountCode discountCode = this.discountCodeService.getByCode(code);
		
		if( discountCode instanceof DiscountDCC) {
			
			return new OutputDiscountDCC(discountCode);
		}
		
		return new OutputDiscounDCP(discountCode) ;
	}
	
	
	
	@GetMapping(value = "/delete")
	public OutputDiscountCodeDTO deleteDiscountCode( @RequestParam Long id) {
		
		DiscountCode discountCode = this.discountCodeService.delete(id);
		
		if( discountCode instanceof DiscountDCC) {
			
			return new OutputDiscountDCC(discountCode);
		}
		
		return new OutputDiscounDCP(discountCode) ;
	}
	
	
	
	
	@PostMapping(value = "categories/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public OutputDiscountCodeDTO  addDiscountCategory(@ModelAttribute InputDiscountCategory inputDiscount) {
		
		DiscountCode discountCode = this.discountCodeService.addInput(inputDiscount);

		if (discountCode instanceof DiscountDCC) {

			return new OutputDiscountDCC(discountCode);
		}

		return new OutputDiscounDCP(discountCode);

		
	}
	
	
	
	@PostMapping(value="/customers/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE,"text/plain;charset=UTF-8" })
	public OutputDiscountCodeDTO  addDiscountCustomer(@RequestBody InputDiscountCustomerDTO discountCustomerDTO) {
			
		System.out.println(discountCustomerDTO);
			DiscountCode discountCode = this.discountCodeService.addInput(discountCustomerDTO);
	
			if (discountCode instanceof DiscountDCC) {
	
				return new OutputDiscountDCC(discountCode);
			}
	
			return new OutputDiscounDCP(discountCode);
	
			
		}
		
	
	
	//class pojos class***************************************************************************************
	@Data
	@NoArgsConstructor
	public class InputDiscountProduct implements InputDiscountIn{
		 
		    private String code;
		    
		    private Long productId;// the product which is affected by reduce
		    
		    private Double discount;

		    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
		    private Date endDate;
		    private Boolean actif;

	}
	
	
	

	

	
	@Data
	@NoArgsConstructor
	public class OutputDiscountProduct {
		 
			private Long id;
		    private String code;
		    private Long productId;// the product which is affected by reduce
		    private Double discount;
		    private Boolean actif;
		    @DateTimeFormat(pattern = "yyyy-MM-dd")
		    private Date endDate;
		    
		 
			   public OutputDiscountProduct(DiscountCode discountCode, InputDiscountProduct inputDiscountProduct) {
				   this.id = discountCode.getId();
				   this.code = discountCode.getCode();
				   this.discount = discountCode.getDiscount();
				   this.actif = discountCode.getActif();
				   this.endDate = discountCode.getEndDate();
				   this.productId = inputDiscountProduct.getProductId();
			   }

	}
	
	
	@Data
	@NoArgsConstructor
	public class InputDiscountCategory implements InputDiscountIn{
		 
		
			
			@NotBlank
			@NotEmpty
		    private String code;
			@NotNull
		    private Long categoryId;
			@NotNull
		    private Double discount;
			@NotNull
		    @DateTimeFormat(pattern = "yyyy-MM-dd")
		    private Date endDate;
			private Boolean actif;
			

	}
	
	
	
	@Data
	@NoArgsConstructor
	public class OutputDiscountCategory implements InputDiscountIn{
		 
			private Long id;
		    private String code;		  
		    private Long categoryId;
		    private Double discount;
		    private Boolean actif;
		    @DateTimeFormat(pattern = "yyyy-MM-dd")
		    private Date endDate;
		    
		   public OutputDiscountCategory(DiscountCode discountCode, InputDiscountCategory inputDiscountCategory) {
			   this.id = discountCode.getId();
			   this.code = discountCode.getCode();
			   this.discount = discountCode.getDiscount();
			   this.actif = discountCode.getActif();
			   this.endDate = discountCode.getEndDate();
			   this.categoryId = inputDiscountCategory.getCategoryId();
		   }

	}
	
	
	
	
	@Data
	@NoArgsConstructor
	public class toggleActif {
		  
		    private Long discountId;
		    private Boolean toggle;
	}
	
	@Data
	@NoArgsConstructor
	public class DistcountDtoCode {
		  
			@NotNull
		    private Long id;
		    @NotNull @NotBlank
		    private String code;
	}
	
	
	@Data
	@NoArgsConstructor
	public class DistcountDtoEndDate {
		  
			@NotNull
		    private Long id;
		    @NotNull 
		    private Date enDate;
	}
	
}

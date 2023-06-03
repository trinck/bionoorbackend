package com.bionoor.api.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bionoor.api.models.Category;
import com.bionoor.api.models.Certificat;
import com.bionoor.api.models.Media;
import com.bionoor.api.models.OrderItem;
import com.bionoor.api.models.Product;
import com.bionoor.api.models.ProductRange;
import com.bionoor.api.models.Review;
import com.bionoor.api.services.ProductService;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@CrossOrigin("*")

public class RestProduct {

	@Autowired
	private ProductService productService;
	@GetMapping(value = "/api/products/search")
	public List<OutputProductDTO> search(@RequestParam @NotEmpty String name) {
		
		return  new OutputProductDTO().fromListProduct(this.productService.findByName(name));
	}
	
	
	
	
	public class InputProductDTO{}
	
	@Data
	@NoArgsConstructor
	public class OutputProductDTO{
		
		    private Long id;
		    private String name; // product name

		    private String code; // product name
		    
		    private String steps; // product steps preparation
		    
		    private String ingredients; // product ingredients
		    

		    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
		    private Date createAt; // creation date 
		    
		   
		  
		    private String description; // product description

		    private Double price; // product price
		    
		   
		    private Long quantity; // product price
		    
		    private Double promotion; // promotion

		   public  OutputProductDTO(Product product) {
			   this.id = product.getId();
			   this.code = product.getCode();
			   this.createAt = product.getCreateAt();
			   this.description = product.getDescription();
			   this.name = product.getName();
			   this.quantity = product.getQuantity();
			   this.promotion = product.getPromotion();
			   this.price = product.getPrice();
			   this.ingredients = product.getIngredients();
			   this.steps = product.getSteps();
		   }
		   
		   public  List<OutputProductDTO> fromListProduct(List<Product> list){
			
			   List<OutputProductDTO> dtos = new ArrayList<>();
			   
			   list.forEach(product ->{
				   dtos.add(new OutputProductDTO(product));
			   });
			   return dtos;
			   
		   }
		   
		   
		  
		   
		   
		 
	}
}


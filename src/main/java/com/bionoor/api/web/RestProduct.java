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

import com.bionoor.api.dto.OutputProductDTO;
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
	public OutputProductDTO search(@RequestParam @NotEmpty String name) {
		
		return  new OutputProductDTO(this.productService.findByName(name));
	}
	
	
	
	
	public class InputProductDTO{}
	
	
}


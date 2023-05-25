package com.bionoor.api.admin;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bionoor.api.models.Category;
import com.bionoor.api.models.Product;
import com.bionoor.api.repositories.CategoryRepository;
import com.bionoor.api.services.CategoryService;
import com.bionoor.api.services.ProductService;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Controller

public class AdminProduct {

	
	@Value("${app.name}")
	private String name;
	@Value("${app.logo}")
	 private String logo;
	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;
	
	
	@GetMapping(value = "/product")
	public String product(Model model,@RequestParam(name = "id") int id) {
		
		
		model.addAttribute("name", name);
		model.addAttribute("id",id);
		model.addAttribute("logo",logo);
		return "products/productview.html";
	}
	
	
	@GetMapping(value = "/productForm")
	public String productForm(Model model) {
		
		
		List<Category> categories = this.categoryService.allCategories();
		model.addAttribute("categories", categories);
		model.addAttribute("name", name);
		
		model.addAttribute("logo",logo);
		InputProductDto  inputProductDto = new InputProductDto();
		model.addAttribute("product", inputProductDto);
		
		return "products/productform.html";
	}
	
	
	@PostMapping(value = "/saveProduct")
	public String saveProduct(Model model,@Valid @ModelAttribute InputProductDto inputProductDto, BindingResult bindingResult) {
		
		List<Category> categories = this.categoryService.allCategories();
		model.addAttribute("categories", categories);
		model.addAttribute("logo",logo);
		model.addAttribute("name", name);
		
		InputProductDto  newinputProductDto = new InputProductDto();
		model.addAttribute("product", newinputProductDto);
		
		if(bindingResult.hasErrors()) {
			
			return "products/productform.html";
		}
		
		this.productService.add(inputProductDto);
		
		
		return "products/productform.html";
	}
	
	
	
	
	
	@GetMapping(value = "/products")
	public String products(Model model) {
		
		model.addAttribute("name", name);
		List<Product> products = new ArrayList<>();
		model.addAttribute("products", products);
		return "products/products.html";
		
	}
	
	
	
	
	
	@Data
	@NoArgsConstructor
	public class InputProductDto{
		
			@NotEmpty @NotBlank
		    private String name; // product name

			@NotEmpty @NotBlank
		    private String code; // product name
		    
		    private String ingredients; // product ingredients
		    
		    private String description; // product description
		    private List<MultipartFile> images = new ArrayList<>();
		   @NotNull @DecimalMin(value = "0")
		    private Double price; // product price
		    
		   @NotNull @Min(value = 0)
		    private Long quantity; // product price
		   
		   @NotNull @Min(value = 0)
		    private Long category; // product price
		    
		    private Double promotion; // promotion

	}
	
}

package com.bionoor.api.admin;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
import com.bionoor.api.models.Invoice;
import com.bionoor.api.models.Product;
import com.bionoor.api.models.Order.OrderStatus;
import com.bionoor.api.repositories.CategoryRepository;
import com.bionoor.api.services.CategoryService;
import com.bionoor.api.services.CategoryServiceIn;
import com.bionoor.api.services.ProductService;
import com.bionoor.api.services.ProductServiceIn;

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

	
	@Autowired
	private CategoryServiceIn categoryService;
	@Autowired
	private ProductServiceIn productService;
	
	
	@GetMapping(value = "/product")
	public String product(Model model,@RequestParam(name = "id") Long id) {
		
		Product product = this.productService.getById(id);
	
		model.addAttribute("id",id);
		model.addAttribute("product", product);
		
		return "products/productview.html";
	}
	
	
	@GetMapping(value = "/productForm")
	public String productForm(Model model) {
		
		
		List<Category> categories = this.categoryService.allCategories();
		model.addAttribute("categories", categories);
	
		InputProductDto  inputProductDto = new InputProductDto();
		model.addAttribute("product", inputProductDto);
		
		return "products/productform.html";
	}
	
	
	@PostMapping(value = "/saveProduct")
	public String saveProduct(Model model,@Valid @ModelAttribute InputProductDto inputProductDto, BindingResult bindingResult) {
		
		List<Category> categories = this.categoryService.allCategories();
		model.addAttribute("categories", categories);
	
		
		InputProductDto  newinputProductDto = new InputProductDto();
		model.addAttribute("product", newinputProductDto);
		
		if(bindingResult.hasErrors()) {
			
			model.addAttribute("product", inputProductDto);
			return "products/productform.html";
			
		}
		
		
		this.productService.add(inputProductDto);
		
		
		return "products/productform.html";
	}
	
	
	
	@GetMapping(value = "/productPages")
	/* this id is for existing invoice*/
	public String page(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, String mc,@RequestParam(defaultValue = "id:ascending") String sort, @RequestParam(defaultValue = "id") String by ) 
	{
		
		
		Page<Product> products;
		
		if(by.equalsIgnoreCase("id")) {
			try {
				products = this.productService.pagesById(page, size,( mc==null || mc.isEmpty())? null:Long.valueOf(mc), sort);
			}catch(NumberFormatException e){
				
				model.addAttribute("error", "The input search must be a number");
				products  = this.productService.pagesById(page, size,null, sort);
			}
			
		}else if(by.equalsIgnoreCase("code")) {
			
			products = this.productService.pagesByCode(page, size, sort, mc);
		}else {
			
			products = this.productService.pagesByName(page, size, sort, mc);
		}
		
		
	
		model.addAttribute("by", by);
		model.addAttribute("totalElements", products.getTotalElements());
		model.addAttribute("pages", new int[products.getTotalPages()]);
		model.addAttribute("mc", mc);
		model.addAttribute("page", page);
		model.addAttribute("size", size);
		model.addAttribute("sort", sort);
		model.addAttribute("totalPages", products.getTotalPages());
		model.addAttribute("products", products.getContent());	
		return "products/products";
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

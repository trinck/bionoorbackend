package com.bionoor.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bionoor.api.models.Category;
import com.bionoor.api.models.DiscountCode;
import com.bionoor.api.repositories.MediaRepository;
import com.bionoor.api.repositories.ProductRepository;
import com.bionoor.api.services.CategoryService;
import com.bionoor.api.services.DiscountCodeService;
import com.bionoor.api.services.ProductService;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Controller
@CrossOrigin("*")
public class AdminCategory {

	@Autowired
	CategoryService categoryService;
	@Autowired
	DiscountCodeService discountCodeService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductRepository productRepository;
	
	
	
	
	@Value("${app.name}")
	private String name;
	@Value("${app.logo}")
	 private String logo;
	
	
	

	@GetMapping(value = "/categories")
	public String categories(Model model) {
		 List<Category> categories = this.categoryService.allCategories();
		model.addAttribute("name", name);
		model.addAttribute("categories", categories);
		 
		//System.out.println(this.productRepository.findAll());
		//System.out.println(categories);
		return "categories/categorieslist.html";
	}
	
	@GetMapping(value = "/categoryform")
	public String categoryForm(Model model) {
		InputCategory newCategory = new InputCategory();
		 List<Category> categories = this.categoryService.allCategories();
		model.addAttribute("name", name);
		model.addAttribute("category", newCategory);
		model.addAttribute("categories", categories);
		return "categories/categoryform.html";
	}
	
	
	@GetMapping(value = "/category")
	public String category(Model model, @RequestParam(value = "id") Long id) {
		
		Category category = this.categoryService.getById(id);
		List<Category> categories = this.categoryService.allCategories();
		List<DiscountCode> discountCodes = category.getDiscountCodes();
		categories.remove(category);
		
		model.addAttribute("category", category);
		model.addAttribute("categories", categories);
		model.addAttribute("discountCodes",discountCodes);
		model.addAttribute("name", name);
		return "categories/categoryview.html";
	}
	
	
	@GetMapping(value = "/deleteCategory")
	public String deleteCategory(Model model,@RequestParam Long id) {
		
		 this.categoryService.delete(id);	
	    return categories(model); 
		
	}
	
	
	@PostMapping(value = "/addCategory")
	public String addCategory(Model model,@ModelAttribute InputCategory category) {
		
		this.categoryService.add(category);
		
	    return categoryForm(model); 
		
	}
	
	
	
	
	
	@NoArgsConstructor
	@Data
	public class InputCategory{
		  
		
			private String name; // name of the category
			
			private MultipartFile image; 
			
		    private Long parent;
		    
		  
		    
	  }
	
	
	
	public class OutputCategory{}
	  
	
	
}

	
  

  
  
  

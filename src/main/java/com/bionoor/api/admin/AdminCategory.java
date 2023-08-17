package com.bionoor.api.admin;

import java.util.List;

import org.apache.logging.log4j.util.StringBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.bionoor.api.models.Order;
import com.bionoor.api.repositories.MediaRepository;
import com.bionoor.api.repositories.ProductRepository;
import com.bionoor.api.security.AppConfig;
import com.bionoor.api.services.CategoryService;
import com.bionoor.api.services.CategoryServiceIn;
import com.bionoor.api.services.DiscountCodeService;
import com.bionoor.api.services.ProductService;
import com.bionoor.api.services.ProductServiceIn;

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
	CategoryServiceIn categoryService;
	@Autowired
	DiscountCodeService discountCodeService;
	
	@Autowired
	private AppConfig appConfig;
	 
	
	
	

		@GetMapping(value = "/categories")
		public String categories(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, String mc,@RequestParam(defaultValue = "id:ascending") String sort, @RequestParam(defaultValue = "id") String by) {

		

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			model.addAttribute("authentication", authentication);

			Page<Category> categories;

			if (by.equalsIgnoreCase("id")) {
				try {
					categories = this.categoryService.findById(page, size,(mc == null || mc.isEmpty()) ? null : Long.valueOf(mc), sort);
				} catch (NumberFormatException e) {

					model.addAttribute("error", "The input search must be a number");
					categories = this.categoryService.findById(page, size, null, sort);
				}

			} else {

				categories = this.categoryService.findByName(page, size, sort, mc);
			} 

			
			model.addAttribute("by", by);
			model.addAttribute("totalElements", categories.getTotalElements());
			model.addAttribute("pages", new int[categories.getTotalPages()]);
			model.addAttribute("mc", mc);
			model.addAttribute("page", page);
			model.addAttribute("size", size);
			model.addAttribute("sort", sort);
			model.addAttribute("totalPages", categories.getTotalPages());
			model.addAttribute("categories", categories.getContent());

			return "categories/categorieslist.html";
		}
	
	
	
	
	
	@GetMapping(value = "/categoryform")
	public String categoryForm(Model model) {
		InputCategory newCategory = new InputCategory();
		 List<Category> categories = this.categoryService.allCategories();
		
		model.addAttribute("category", newCategory);
		model.addAttribute("categories", categories);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		model.addAttribute("authentication", authentication);
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
	
		return "categories/categoryview.html";
	}
	
	
	@GetMapping(value = "/deleteCategory")
	public String deleteCategory(Model model,@RequestParam Long id,  @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, String mc,@RequestParam(defaultValue = "id:ascending") String sort, @RequestParam(defaultValue = "id") String by) {
		
		
		
		String redirect = String.format("page=%d&size=%d&mc=%s&sort=%s&by=%s", page,size,mc,sort,by);
		
		 this.categoryService.delete(id);	
	    return "redirect:/categories?"+redirect; 
		
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

	
  

  
  
  

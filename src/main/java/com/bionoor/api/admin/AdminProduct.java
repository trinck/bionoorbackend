package com.bionoor.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bionoor.api.models.Category;
import com.bionoor.api.repositories.CategoryRepository;

@Controller

public class AdminProduct {

	
	@Value("${app.name}")
	private String name;
	@Value("${app.logo}")
	 private String logo;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	
	@GetMapping(value = "/product")
	public String product(Model model,@RequestParam(name = "id") int id) {
		
		model.addAttribute("name", name);
		model.addAttribute("id",id);
		model.addAttribute("logo",logo);
		return "products/productview.html";
	}
	
	
	@GetMapping(value = "/addproduct")
	public String productForm(Model model) {
		
		
		List<Category> categories = this.categoryRepository.findAll();
		model.addAttribute("categories", categories);
		model.addAttribute("name", name);
		
		model.addAttribute("logo",logo);
		return "products/productform.html";
	}
	
	
	
	
	
	@GetMapping(value = "/products")
	public String products(Model model) {
		
		model.addAttribute("name", name);
		return "products/products.html";
	}
	
}

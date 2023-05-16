package com.bionoor.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.bionoor.api.models.Category;
import com.bionoor.api.services.CategoryService;

@Controller

public class AdminCategory {

	@Autowired
	CategoryService categoryService;
	@Value("${app.name}")
	private String name;
	@Value("${app.logo}")
	 private String logo;
	
	
	

	@GetMapping(value = "/categories")
	public String categories(Model model) {
		
		model.addAttribute("name", name);
		return "categories/categorieslist.html";
	}
	
	@GetMapping(value = "/categoryform")
	public String categoryForm(Model model) {
		Category newCategory = new Category();
		model.addAttribute("name", name);
		model.addAttribute("category", newCategory);
		return "categories/categoryform.html";
	}
	
	
	@GetMapping(value = "/category")
	public String category(Model model, @RequestParam(value = "id") int id) {
		
		model.addAttribute("name", name);
		return "categories/categoryview.html";
	}
	
	
	@GetMapping(value = "/addCategory")
	public String addCategory(Model model, @RequestBody() Category category) {
		
		
		this.categoryService.add(category);
		
		return categoryForm(model);
	}
	
}

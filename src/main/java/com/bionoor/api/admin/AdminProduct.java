package com.bionoor.api.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class AdminProduct {

	
	@Value("${app.name}")
	private String name;
	@Value("${app.logo}")
	 private String logo;
	
	
	
	@GetMapping(value = "/product")
	public String product(Model model,@RequestParam(name = "id") int id) {
		
		model.addAttribute("name", name);
		model.addAttribute("id",id);
		model.addAttribute("logo",logo);
		return "products/productview.html";
	}
	
	
	@GetMapping(value = "/addproduct")
	public String productForm(Model model) {
		
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

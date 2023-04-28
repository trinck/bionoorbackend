package com.bionoor.api.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

	@Value("${app.name}")
	private String name;
	
	@GetMapping(value = "/")
	public String index(Model model) {
		
		model.addAttribute("name", name);
		return "index";
	}
	
	
	
	@GetMapping(value = "/users")
	public String home(Model model) {
		model.addAttribute("name", name);
		
		return "users.html";
	}
	
	
	@GetMapping(value = "/products")
	public String products(Model model) {
		
		model.addAttribute("name", name);
		return "products/products.html";
	}
	
	
	@GetMapping(value = "/categories")
	public String categories(Model model) {
		
		model.addAttribute("name", name);
		return "categories/categorieslist.html";
	}
	
	@GetMapping(value = "/orders")
	public String orders(Model model) {
		
		model.addAttribute("name", name);
		return "orders/orders.html";
	}
	
	
	@GetMapping(value = "/product")
	public String product(Model model,@RequestParam(name = "id") int id) {
		
		model.addAttribute("name", name);
		model.addAttribute("id",id);
		return "products/productview.html";
	}
	
	
	@GetMapping(value = "/order")
	public String order(Model model,@RequestParam(name = "id") int id) {
		
		model.addAttribute("name", name);
		model.addAttribute("id",id);
		return "orders/orderview.html";
	}
}

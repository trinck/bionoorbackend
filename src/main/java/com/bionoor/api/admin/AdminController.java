package com.bionoor.api.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	public String login(Model model) {
		
		model.addAttribute("name", name);
		return "products/products.html";
	}
}

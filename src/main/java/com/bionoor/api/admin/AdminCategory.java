package com.bionoor.api.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class AdminCategory {

	
	
	@Value("${app.name}")
	private String name;
	@Value("${app.logo}")
	 private String logo;
	
	
	

	@GetMapping(value = "/categories")
	public String categories(Model model) {
		
		model.addAttribute("name", name);
		return "categories/categorieslist.html";
	}
	
}

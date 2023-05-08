package com.bionoor.api.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class AdminCustomer {
	
	@Value("${app.name}")
	private String name;
	@Value("${app.logo}")
	 private String logo;
	
	@GetMapping(value = "/customers")
	public String customers(Model model) 
	{
		
		model.addAttribute("name", name);
		return "customers/customers";
		
	}
	
	@GetMapping(value = "/customer")
	public String customer(Model model,@RequestParam(value = "id") int id) 
	{
		
		model.addAttribute("name", name);
		return "customers/customerview";
		
	}

}

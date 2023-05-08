package com.bionoor.api.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class AdminOrder {

	
	@Value("${app.name}")
	private String name;
	@Value("${app.logo}")
	 private String logo;
	
	
	

	@GetMapping(value = "/order")
	public String order(Model model,@RequestParam(name = "id") int id) {
		
		model.addAttribute("name", name);
		model.addAttribute("id",id);
		
		return "orders/orderview.html";
	}
	
	@GetMapping(value = "/orders")
	public String orders(Model model) {
		
		model.addAttribute("name", name);
		return "orders/orders.html";
	}
}

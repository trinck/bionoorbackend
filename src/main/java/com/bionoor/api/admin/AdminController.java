package com.bionoor.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class AdminController {

	@Value("${app.name}")
	private String name;
	@Value("${app.logo}")
	 private String logo;
	
	@GetMapping(value = "/")
	public String index(Model model) {
		
		model.addAttribute("name", name);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("authentication", authentication);
		return "index";
	}
	
	
	@GetMapping(value = "/login")
	public String login(Model model) {
		
		model.addAttribute("name", name);
		
		return "login";
	}
	
	
	

	
	
	
}

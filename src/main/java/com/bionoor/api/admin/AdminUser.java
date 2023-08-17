package com.bionoor.api.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class AdminUser {

	
	
	
	@GetMapping(value = "/users")
	public String users(Model model) {
		
		
		return "users/users.html";
	}
	
}

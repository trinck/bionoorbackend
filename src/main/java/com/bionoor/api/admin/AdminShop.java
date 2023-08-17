package com.bionoor.api.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AdminShop {

	
	

	
	
	@GetMapping(value = "/shop")
	public String shop(Model model) {
		
		return "shop/shop";
	}
	
}

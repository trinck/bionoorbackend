package com.bionoor.api.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/users")
public class ProductController {

	
	@GetMapping(value = "/users")
	public String users() {
		
		return "vous communiquez bien avec bionoor api/users";
	}
	
}


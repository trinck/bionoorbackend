package com.bionoor.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bionoor.api.models.Customer;
import com.bionoor.api.services.CustomerService;

@Controller
public class AdminCustomer {
	
	@Value("${app.name}")
	private String name;
	@Value("${app.logo}")
	 private String logo;
	
	@Autowired
	private CustomerService customerService;
	
	
	@GetMapping(value = "/customers")
	public String customers(Model model) 
	{
		List<Customer> customers = this.customerService.getCustomers();
		model.addAttribute("name", name);
		model.addAttribute("customers", customers);)
		return "customers/customers";
		
	}
	
	@GetMapping(value = "/customer")
	public String customer(Model model,@RequestParam(value = "id") int id) 
	{
		
		model.addAttribute("name", name);
		return "customers/customerview";
		
	}

}

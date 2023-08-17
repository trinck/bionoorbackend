package com.bionoor.api.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bionoor.api.models.Category;
import com.bionoor.api.models.Customer;
import com.bionoor.api.models.Product;
import com.bionoor.api.services.CategoryService;
import com.bionoor.api.services.CustomerService;
import com.bionoor.api.services.ProductService;

@Controller
public class AdminCustomer {
	
	
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping(value = "/customers")
	public String customers(Model model) 
	{
		List<Customer> customers = this.customerService.getCustomers();
		
		model.addAttribute("customers", customers);
		return "customers/customers";
		
	}
	
	@GetMapping(value = "/customerView")
	public String customer(Model model,@RequestParam(value = "id") UUID id) 
	{
	
		Customer customer = this.customerService.getCustomerById(id);
		
		model.addAttribute("customer", customer);
		
		List<Product> products = new ArrayList<>();
		products = this.productService.allProducts();
		
		List<Category> categories = new ArrayList<>();
		categories = this.categoryService.allCategories();
		model.addAttribute("products", products);
		model.addAttribute("categories", categories);
		
		return "customers/customerview";
		
	}

}

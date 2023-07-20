package com.bionoor.api.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bionoor.api.dto.InputCustomerDTO;
import com.bionoor.api.dto.OutputCountry;
import com.bionoor.api.dto.OutputCustomerDTO;
import com.bionoor.api.models.Customer;
import com.bionoor.api.services.CustomerService;

@RestController
@RequestMapping("api/customers")
public class RestCustomer {

	
	 @Autowired
	 private  CustomerService customerService;
	 
	 
	 		//send a email confirmation 
			@GetMapping(value = "/emailConfirmation")
			public Map<String,String> emailConfirmation(@RequestParam UUID userId) {
				
				 this.customerService.sendEmailConfirmation(userId);
				 
				 return Map.of("message","Email verification has successfuly sent") ;
			  
			}
			
			

	    @GetMapping("/{id}")
	    public OutputCustomerDTO getCustomerById(@PathVariable UUID id) {
	        Customer customer = customerService.getCustomerById(id);
	       return new OutputCustomerDTO(customer);
	    }

	    @PostMapping(value = "/save")
	    public OutputCustomerDTO createCustomer(@RequestBody InputCustomerDTO inputCustomerDTO) {
	       
	        Customer createdCustomer = customerService.createCustomer(inputCustomerDTO);
	        OutputCustomerDTO customerDTO = new OutputCustomerDTO(createdCustomer);
	        return customerDTO;
	    }
	    
	    
	    
	    @PostMapping(value = "/toogle/enabled")
	    public OutputCustomerDTO toogleEnabled(@RequestParam UUID id, @RequestParam Boolean enabled ) {
	       
	        Customer createdCustomer = customerService.toogleEnabled(id, enabled);
	        OutputCustomerDTO customerDTO = new OutputCustomerDTO(createdCustomer);
	        return customerDTO;
	    }
	    
	    
	    
	    
	    @GetMapping
	    public List<OutputCustomerDTO> getCustomers() {
	       
	    	List<Customer> customers = this.customerService.getCustomers();
	        
	        List<OutputCustomerDTO> customerDTOs = new ArrayList<>();
	        customers.forEach(customer ->{
	        	customerDTOs.add(new OutputCustomerDTO(customer));
	        });
	        
	        return customerDTOs;
	    }

	    

	    @DeleteMapping("/{id}")
	    public OutputCustomerDTO deleteCustomer(@PathVariable UUID id) {
	      
	         Customer  customer =   this.customerService.deleteCustomer(id);
	           
	         return new OutputCustomerDTO(customer);
	    }

	   
	
}

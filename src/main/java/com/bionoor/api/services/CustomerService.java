package com.bionoor.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.dto.InputCustomerDTO;
import com.bionoor.api.models.Address;
import com.bionoor.api.models.Customer;
import com.bionoor.api.models.SimpleCustomer;
import com.bionoor.api.repositories.CustomerRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
@Data
@NoArgsConstructor
public class CustomerService {

	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CityService cityService;
	
	public Customer getCustomerById(Long id) {
        
		
	   Customer customer =	customerRepository.findById(id).orElse(null);
	   
	   if(customer == null) {
		   throw new EntityNotFoundException("customer with id: "+id+" did no found");
	   }
	   
	   
	   return customer;
    }
	

    public Customer createCustomer(Customer customer) {
        // Additional logic before creating the customer, if needed
        return customerRepository.save(customer);
    }
    
    
    public List<Customer> getCustomers() {
        // Additional logic before creating the customer, if needed
        return this.customerRepository.findAll();
    }
    
    
    public Customer createCustomer(InputCustomerDTO customer) {
        
       Customer newCustomer = new SimpleCustomer(customer);
       
       Address address = new Address(customer.getAddress());
       address.setCustomer(newCustomer);
       address.setCity(this.cityService.getCityById(customer.getAddress().getCity()));
       newCustomer.setAddress(address);
       
       return this.customerRepository.save(newCustomer);
       
    }

    public Customer updateCustomer(Customer customer) {
        // Additional logic before updating the customer, if needed
        return customerRepository.save(customer);
    }

    public Customer deleteCustomer(Long id) {

    	Customer customer = this.getCustomerById(id);
        customerRepository.deleteById(id);
        
        return customer;
    }
}

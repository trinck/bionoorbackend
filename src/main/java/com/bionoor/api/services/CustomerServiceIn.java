package com.bionoor.api.services;

import java.util.List;
import java.util.UUID;
import com.bionoor.api.dto.InputCustomerDTO;
import com.bionoor.api.models.Address;
import com.bionoor.api.models.Customer;
import com.bionoor.api.models.SimpleCustomer;

import jakarta.persistence.EntityNotFoundException;

public interface CustomerServiceIn {

	
public void sendEmailConfirmation(UUID userId) ;
	
	
public Customer getCustomerById(UUID id) ;





public Customer toogleEnabled(UUID id, Boolean enabled);


public Customer emailConfirmationCallback(UUID id) ;




public Customer createCustomer(Customer customer);


public List<Customer> getCustomers() ;


public Customer createCustomer(InputCustomerDTO customer) ;

public Customer updateCustomer(Customer customer) ;

public Customer deleteCustomer(UUID id) ;
	
}

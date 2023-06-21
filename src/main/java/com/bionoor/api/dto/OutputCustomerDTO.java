package com.bionoor.api.dto;

import java.util.ArrayList;
import java.util.List;

import com.bionoor.api.models.Address;
import com.bionoor.api.models.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutputCustomerDTO {

	
	
		private Long id;

	    private String username;

	    private String firstName;

	    private String lastName;

	    private List<OutputDiscountCodeDTO> usedDiscountCodes = new ArrayList<>();

	    private List<OutputDiscountDCC> discountDCCs = new ArrayList<>();

	    private List<OutputOrderDTO> orders = new ArrayList<>();

	    private OutputAddressDTO address;

	    private String discriminatorValue;


	    private String email;

	    private String tel;

	    private boolean confirmed;
	    private String password;
	    private boolean enabled;

	    // Constructors, getters, and setters

	
	public OutputCustomerDTO(Customer customer) {
		
		
			this.id = customer.getId();
			this.enabled = customer.isEnabled();
			this.confirmed = customer.isConfirmed();
			this.username = customer.getUsername();
	        this.firstName = customer.getFirstName();
	        this.lastName = customer.getLastName();
	        
	        this.tel = customer.getTel();
	         this.email = customer.getEmail();
	         this.password = customer.getPassword();
	         this.address =  customer.getAddress()!= null? new OutputAddressDTO(customer.getAddress()):null;
	         this.discriminatorValue = customer.getDiscriminatorValue();
	         
	         customer.getUsedDiscountCodes().forEach(code -> {
	        	 this.usedDiscountCodes.add(new OutputDiscountCodeDTO(code));
	        	 
	         }); 
	         
	         
	         customer.getDiscountDCCs().forEach(code -> this.discountDCCs.add(new OutputDiscountDCC(code)));	
	         
	         customer.getOrders().forEach(order -> this.orders.add(new OutputOrderDTO(order)));
	         
	}
	
	
}

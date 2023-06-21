package com.bionoor.api.models;

import com.bionoor.api.dto.InputCustomerDTO;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue(value = "Simple")
public class SimpleCustomer extends Customer{

	
	
	public SimpleCustomer(InputCustomerDTO customerDTO) {
		
		super(customerDTO);
	}
}

package com.bionoor.api.dto;

import java.util.UUID;

import com.bionoor.api.models.Address;
import com.bionoor.api.models.City;
import com.bionoor.api.models.Customer;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OutputAddressDTO {

	
	
	private Long id;
	
	
	private String street;
	
	
	private UUID customerId;
	
	
	private OutputCityDTO city;
	
	public OutputAddressDTO(Address address ) {
		this.id = address.getId();
		this.street = address.getStreet();
		this.customerId = address.getCustomer().getId();
		
		this.city = new OutputCityDTO(address.getCity());
	}
}

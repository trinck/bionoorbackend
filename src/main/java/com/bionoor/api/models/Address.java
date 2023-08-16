package com.bionoor.api.models;

import java.io.Serializable;

import com.bionoor.api.dto.InputAddressDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
public class Address extends GenericBionoorEntity implements Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String street;
	
	@OneToOne()
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@OneToOne
	@JoinColumn(name = "city_id")
	private City city;
	
	public Address(InputAddressDTO addressDTO) {
		this.street = addressDTO.getStreet();
	}
}

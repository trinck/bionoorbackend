package com.bionoor.api.dto;

import com.bionoor.api.models.City;
import com.bionoor.api.models.Country;
import com.bionoor.api.models.Customer;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OutputCityDTO {

	

	private Long id;
	
	
	private String name;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	private String countryName;
	private Long countryId;
	
	public OutputCityDTO( City city) {
		this.id = city.getId();
		this.name = city.getName();
		this.countryName = city.getCountry() != null? city.getCountry().getName():"";
		this.countryId = city.getCountry() != null? city.getCountry().getId(): null;
		
	}
}

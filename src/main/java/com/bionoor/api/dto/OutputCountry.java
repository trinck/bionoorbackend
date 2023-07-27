package com.bionoor.api.dto;

import java.util.ArrayList;
import java.util.List;

import com.bionoor.api.models.City;
import com.bionoor.api.models.Country;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OutputCountry {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	

	@Column(unique = true)
	private String code;
	
	@OneToMany(mappedBy = "country")
	private List<OutputCityDTO> cities = new ArrayList();
	
	
	public OutputCountry(Country country) {
		
		this.id = country.getId();
		this.name = country.getName();
		this.code = country.getCode();
		
		country.getCities().forEach(city ->{
			this.cities.add(new OutputCityDTO(city));
		});
		
	}
}

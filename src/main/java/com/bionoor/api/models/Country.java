package com.bionoor.api.models;

import java.util.ArrayList;
import java.util.List;

import com.bionoor.api.dto.InputCountryDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "countries")
public class Country {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	

	@Column(unique = true)
	private String code;
	
	@OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<City> cities = new ArrayList();
	
	
	public Country(InputCountryDTO countryDTO) {
		
		this.name = countryDTO.getName();
		this.code = countryDTO.getCode();
		
	}
}
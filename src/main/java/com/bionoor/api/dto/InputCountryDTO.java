package com.bionoor.api.dto;

import java.util.ArrayList;
import java.util.List;

import com.bionoor.api.models.City;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InputCountryDTO {

	
	
	private String name;
	
	private String code;
	
	private List<InputCityDTO> cities = new ArrayList();
	
	
}

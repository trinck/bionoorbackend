package com.bionoor.api.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class InputAddressDTO {

	
	private String street;
	
	private Long city;
}

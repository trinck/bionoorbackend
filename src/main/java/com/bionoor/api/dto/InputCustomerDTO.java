package com.bionoor.api.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InputCustomerDTO {

	
	private String username;
    
    private String firstName;
    
    private String lastName;
    
    
    private InputAddressDTO address;
   
    
    private String email;
    
    private String password;
    
    private String tel;
    
}

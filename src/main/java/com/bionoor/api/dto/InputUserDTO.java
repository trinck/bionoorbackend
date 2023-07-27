package com.bionoor.api.dto;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputUserDTO {



   @NotBlank
   @NotEmpty
    private String firstName; // user's firstname

   @NotBlank
   @NotEmpty
    private String lastName; // user's lastname

   @NotBlank
   @NotEmpty
    private String username; // username

   @NotEmpty
   @NotBlank
   @Email
    private String email; // user's email address

   @NotEmpty
   @NotBlank
    private String password; // user's password (stored as a hash)

   @NotEmpty
   @NotBlank
    private String confirmation;
    // other properties and methods
  
    private List<String> roles;

	
}

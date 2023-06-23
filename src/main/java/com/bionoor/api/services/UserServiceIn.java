package com.bionoor.api.services;

import java.util.UUID;

import com.bionoor.api.dto.InputUserDTO;
import com.bionoor.api.models.Role;
import com.bionoor.api.models.User;

public interface UserServiceIn {

	
  public User createUser(InputUserDTO inputUserDTO);
  public User createUser(User user);
  public User addRoleToUser(String role, UUID userId);
  public User deleteRole(String role);
  public Role addRole(Role role);
  
  public User loadUserByUsername(String username);
	
}

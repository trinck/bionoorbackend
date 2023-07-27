package com.bionoor.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.bionoor.api.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	
	
	public Role  findByRole(String role);
	
	//public List<Role>	findbyRoleIn(List<String> roles);
}

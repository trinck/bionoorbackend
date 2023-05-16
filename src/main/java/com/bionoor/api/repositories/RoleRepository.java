package com.bionoor.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import com.bionoor.api.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}

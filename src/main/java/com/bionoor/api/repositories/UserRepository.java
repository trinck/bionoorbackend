package com.bionoor.api.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bionoor.api.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {

	
	public Optional<User> findByUsername(String username);
}

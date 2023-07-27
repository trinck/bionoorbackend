package com.bionoor.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bionoor.api.dto.InputUserDTO;
import com.bionoor.api.exceptions.EntityAlreadyExists;
import com.bionoor.api.exceptions.PasswordException;
import com.bionoor.api.models.Role;
import com.bionoor.api.models.User;
import com.bionoor.api.repositories.RoleRepository;
import com.bionoor.api.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
@Data
@NoArgsConstructor
@Transactional
public class UserService implements UserServiceIn{

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public User createUser(InputUserDTO inputUserDTO) {
		
		User appUser = this.userRepository.findByUsername(inputUserDTO.getUsername()).orElse(null);
		
		if(appUser != null) {
			throw new EntityAlreadyExists(String.format("User with username %s already exists", inputUserDTO.getUsername()));
		}
		
		if(inputUserDTO.getPassword().equals(inputUserDTO.getConfirmation())) {
			
			throw new PasswordException(String.format("password confimation failed : %", inputUserDTO.getUsername()));

		}
		
		List<Role> roles = inputUserDTO.getRoles().stream().map(role -> this.roleRepository.findByRole(role)).toList();
		
		
		appUser = User.builder()
				.email(inputUserDTO.getEmail())
				.username(inputUserDTO.getUsername())
				.firstName(inputUserDTO.getFirstName())
				.lastName(inputUserDTO.getLastName())
				.roles(roles)
				.password(this.encoder.encode(inputUserDTO.getPassword()))
				.build();
		
		return this.userRepository.save(appUser);
	}
	
	
	
	@Override
	public User createUser(User user) {
		
		User appUser = this.userRepository.findByUsername(user.getUsername()).orElse(null);
		
		if(appUser != null) {
			throw new EntityAlreadyExists(String.format("User with username %s already exists", user.getUsername()));
		}
		
		
		user.setPassword(encoder.encode(user.getPassword()));
		return this.userRepository.save(user);
	}
	
	
	

	@Override
	public User addRoleToUser(String role, UUID userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User deleteRole(String role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role addRole(Role role) {
		
		return this.roleRepository.save(role);
		
	}

	@Override
	public User loadUserByUsername(String username) {
		
		User user = this.userRepository.findByUsername(username).orElseThrow();
		
		return user;
	}
    
    
	 
	
}


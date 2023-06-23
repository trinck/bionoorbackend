package com.bionoor.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bionoor.api.models.Role;
import com.bionoor.api.models.User;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Service
@NoArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

	
	@Autowired
	private UserServiceIn userServiceIn;
	
	@Autowired
	private PasswordEncoder encoder;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User appUser = this.userServiceIn.loadUserByUsername(username);
		if(appUser == null) {
			throw new EntityNotFoundException("User with username: "+username+" doesn't exists");
		}
		
		
		String[] roles =  appUser.getRoles().stream().map(rol -> rol.getRole()).toArray(String[]::new);
		
		UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
				.username(username)
				.password(appUser.getPassword())
				.roles(roles)
				.build();
		
		
		return userDetails;
	}

}

package com.bionoor.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig  {
	
	
	public SecurityConfig() {
	
		// TODO Auto-generated constructor stub
	
	}
	
	
	 @Bean
	  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			/*
			 * http.cors().and().csrf().disable()
			 * .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			 * .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
			 * and() .authorizeRequests().antMatchers("/api/auth/**").permitAll()
			 * .antMatchers("/api/test/**").permitAll() .anyRequest().authenticated();
			 */
	    
	    // http....;
	    
	    return http.build();
	  }
	
	

}

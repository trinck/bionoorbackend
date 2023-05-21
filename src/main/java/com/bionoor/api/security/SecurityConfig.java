package com.bionoor.api.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
public class SecurityConfig    {
	
	
	public SecurityConfig() {
	
		// TODO Auto-generated constructor stub
	
	}
	
	
	@Bean                                                             
	public UserDetailsService userDetailsService() throws Exception {
		// ensure the passwords are encoded properly
		UserBuilder users = User.withDefaultPasswordEncoder();
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(users.username("user").password("password").roles("USER").build());
		manager.createUser(users.username("admin").password("password").roles("USER","ADMIN").build());
		return manager;
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
		 http.authorizeHttpRequests().anyRequest().permitAll();
	     http.formLogin();
	    return http.csrf().disable().build();
	  }
	
}

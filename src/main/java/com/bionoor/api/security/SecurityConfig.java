package com.bionoor.api.security;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.bionoor.api.models.PaymentMethod;
import com.bionoor.api.models.Role;
import com.bionoor.api.models.User;
import com.bionoor.api.services.PaymentServiceImpl;
import com.bionoor.api.services.UserDetailsServiceImpl;
import com.bionoor.api.services.UserServiceIn;

import lombok.Data;
import lombok.NoArgsConstructor;


@Configuration
@EnableWebSecurity
@Data
@NoArgsConstructor
public class SecurityConfig    {
	
  
	@Autowired
 private CustomAuthenticationEntryPoint authenticationEntryPoint;
	
//	@Bean                                                             
	public UserDetailsService userDetailsService() throws Exception {
		// ensure the passwords are encoded properly
		UserBuilder users = org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder();
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(users.username("user").password("password").roles("USER").build());
		manager.createUser(users.username("admin").password("password").roles("USER","ADMIN").build());
		return manager;
	}
	
	
	 @Bean
	  public SecurityFilterChain filterChain(HttpSecurity http, UserDetailsServiceImpl userDetailsServiceImpl) throws Exception {
			/*
			 * http.cors().and().csrf().disable()
			 * .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			 * .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
			 * and() .authorizeRequests().antMatchers("/api/auth/**").permitAll()
			 * .antMatchers("/api/test/**").permitAll() .anyRequest().authenticated();
			 */
	    
	    // http....;
		
		
		 http
		 .cors().disable()
		 .csrf().disable()
		 .authorizeHttpRequests(authorizeRequest -> {
			 
			 authorizeRequest.requestMatchers("/assets/**", "/api/**", "/webjars/**", "/css/**", "/images/**").permitAll();
		 })
		 
		 .authorizeHttpRequests().anyRequest().authenticated()
		 .and()
		 .userDetailsService(userDetailsServiceImpl)
		 .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
		 .and()
		 .formLogin()
		 .loginPage("/login")
		 .defaultSuccessUrl("/")
		 .permitAll();
		 
	     
	    // http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
	    
	    return  http.build();
	  }
	 
	 @Bean
	 public PasswordEncoder passwordEncoder() {
		 
		 return new BCryptPasswordEncoder();
	 }
	
	 
	// @Bean
	 public CommandLineRunner commandLineinitRoles( UserServiceIn userServiceIn, PaymentServiceImpl paymentServiceImpl) {
		 
		 return args ->{
			
			 Role role = Role.builder()
						.role("ADMIN")
						.description("user can do anything ")
						.build();
				role = userServiceIn.addRole(role);
				
				User user = User.builder()
						.email("trinckmouloungui@gmail.com")
						.username("admin")
						.password("1234")
						.firstName("Gontran")
						.lastName("Mouloungui")
						.roles(List.of(role))
						.build();;
				
						userServiceIn.createUser(user);
						
					PaymentMethod paymentMethod = PaymentMethod.builder()
							.name("Pay as delivered")
							.description("Pay your invoice once you received your Order")
							.build();
					
					paymentServiceImpl.addPaymentMethod(paymentMethod);
		 };
	 }
	 
}

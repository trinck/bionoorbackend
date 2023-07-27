package com.bionoor.api.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
	    	
           response.sendRedirect("/login");
      
    }

    private boolean isClientRequest(HttpServletRequest request) {
        // Implement your logic to determine if the request is from a client
        // For example, you can check the request URL or headers
        // Return true if it's a client request, false otherwise
    	
    	
    	return true;
    }
    
		
    
    
}



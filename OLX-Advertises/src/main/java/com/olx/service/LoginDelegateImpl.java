package com.olx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service 
public class LoginDelegateImpl implements LoginDelegate {

	@Autowired
	RestTemplate restTemplate;

	
	
	// Overriding the abstract functions
	@Override
	@CircuitBreaker(name="TOKEN-VALIDATION-SERVICE",fallbackMethod="fallbackValidToken")
	public boolean isValidToken(String authToken) {
		// You have to pass header for sending token
		HttpHeaders headers = new HttpHeaders();
		// Here we set authorization and pass authToken here
		// You pass the token header to this http entity
		// And here you call exchange() function
		headers.set("Authorization", authToken);
		
		// Writing code to communicate with the server and validate the login server and validate the token
		// Since we are returning boolean value we are writing Boolean.class
//		boolean isValidToken = this.restTemplate.getForObject("http://localhost:9000/olx/user/validate/token", Boolean.class);
//		return isValidToken;
		
		HttpEntity entity = new HttpEntity(headers);
		ResponseEntity<Boolean> result = 
				// http://localhost:9000/olx/user/validate/token
				// "http://auth-service/olx/user/validate/token",
				this.restTemplate.exchange("http://API-GATEWAY/olx/user/validate/token", 
						                    HttpMethod.GET, 
						                    entity, 
						                    Boolean.class);
		return result.getBody();
	}
	
	public boolean fallbackValidToken(String authToken,Exception ex) {
		System.out.println("fallbackValidToken method called: " + ex );
	    return false;
	}
	
}

package com.olx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

// Implementing MasterDataDelegate interface
// Marking it as a service because I am injecting and autowriting this
@Service
public class MasterDataDelegateImpl implements MasterDataDelegate {

	// To use rest template I need to inject rest template
	@Autowired
	RestTemplate restTemplate;
	
	
	// Create an object so for that I create a bean
	// Injecting bean RestTemplate into my IoC container so that it will be used to make a remote call
	// This bean I can either put in Application class or in delegate interface
	// Whatever object we are using they have to be registered as a bean into the IoC container
	/*
	 * @Bean public RestTemplate getRestTemplate() { return new RestTemplate(); }
	 */
	
	@Override
	@CircuitBreaker(name="STATUS-FROM-MASTER-DATA-SERVICE",fallbackMethod="fallback")
	public String getStatusById(int statusId) {		
		/* Using getForEntity() */
		 
		ResponseEntity<String> entityStatusText = this.restTemplate.getForEntity("http://localhost:9001/olx/advertise/status/" + statusId, 
				String.class);	
		return entityStatusText.getBody();			
	}
	
	
	/* fallback() method */
	public String fallback(int statusId, Exception ex) {
		System.out.println("Fallback method called: " + ex);
		return null;
	}
	
	
	@Override
	public String getCategoryById(int categoryId) {			 
		ResponseEntity<String> entityCategoryText = this.restTemplate.getForEntity("http://localhost:9001/olx/advertise/category/" + categoryId, 
				String.class);	
		return entityCategoryText.getBody();			
	}
}

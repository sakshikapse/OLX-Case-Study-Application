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
	
	@Override
	@CircuitBreaker(name="STATUS-FROM-MASTER-DATA-SERVICE",fallbackMethod="fallback")
	public String getStatusById(int statusId) {		
		/* Using getForEntity() */
		 
		ResponseEntity<String> entityStatusText = 
				// http://masterdata-service/olx/advertise/status/
				this.restTemplate.getForEntity("http:/masterdata-service/olx/advertise/status/" + statusId, 
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
		ResponseEntity<String> entityCategoryText = 
				// http://masterdata-service/olx/advertise/category/
				this.restTemplate.getForEntity("http://API-GATEWAY/olx/advertise/category/" + categoryId, 
				String.class);	
		return entityCategoryText.getBody();			
	}
}

package com.olx.actuator;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import com.olx.repository.AdvertiseRepository;

@Component
@Endpoint(id = "advertise-stats")
public class CustomAdvertiseActuator {

	@Autowired 
	AdvertiseRepository advertiseRepository;
	
	long totalNoOfAdvertises = 0;
	long totalNoOfActiveAdvertises = 0;
	long totalNoOfClosedAdvertises = 0;
	long totalNoOfOpenAdvertises = 0;
	
	private static Map<String,Long> advertisestats = new HashMap<String,Long>();
	
	
	@PostConstruct
	public void initialize() {
		totalNoOfAdvertises = this.advertiseRepository.findByCount();
		totalNoOfActiveAdvertises = this.advertiseRepository.findActiveCount();
	    totalNoOfClosedAdvertises = this.advertiseRepository.findClosedCount();
	    totalNoOfOpenAdvertises = this.advertiseRepository.findOpenCount();
		
		advertisestats.put("Number of Advertises", totalNoOfAdvertises);
		advertisestats.put("Number of Active advertises", totalNoOfActiveAdvertises);
		advertisestats.put("Number of Closed Advertises", totalNoOfClosedAdvertises);
		advertisestats.put("Number of Open Advertises", totalNoOfOpenAdvertises);
	}
	
	
	@ReadOperation
	public Map<String, Long> getAdvertiseStats(){
		return advertisestats;
	}
}

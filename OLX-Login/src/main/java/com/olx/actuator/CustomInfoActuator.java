package com.olx.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import com.olx.repository.UserRepository;

@Component
public class CustomInfoActuator implements InfoContributor{


	@Autowired
	UserRepository userRepository;
	
	@Override
	public void contribute(Builder builder) {
		int totalCountOfRegisteredUser = userRepository.findByCount();
		builder.withDetail("Total number of registered users", totalCountOfRegisteredUser);
		
		int totalCountofActiveUser = userRepository.findActiveCount();
		builder.withDetail("Total number of Active users", totalCountofActiveUser);
			
	}
}

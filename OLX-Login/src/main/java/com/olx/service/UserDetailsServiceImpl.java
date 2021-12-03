package com.olx.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.olx.entity.UserEntity;
import com.olx.repository.UserRepository;

// Adding service annotation because it is a service
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	
	@Autowired
	UserRepository userRepository;
	
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Here you write busienss logic for username and password
		List<UserEntity> userEntityList = userRepository.findByUsername(username);
		if(userEntityList.size()>0) {      // User is found
			UserEntity userEntity = userEntityList.get(0);
			
			// Granted authorty is an array list 
			// Creating a list of granted authority
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			 
			// Whenever you creating some simple names in the database user table, make sure that the roles always start with word ROLE it it compulsory.
			// Every role in security has to start with " ROLE_ " it is compulsory 
			
			// Added authoritites - Passing object of the granted authority
			authorities.add(new SimpleGrantedAuthority(userEntity.getRoles()));
			// Creating username
			// Getting password from userEntity
		    // Passing authorities
			User user = new User(username, passwordEncoder.encode(userEntity.getPassword()),authorities);
			// Returning user to the client
			return user;
		}
		throw new UsernameNotFoundException(username);
	}

}

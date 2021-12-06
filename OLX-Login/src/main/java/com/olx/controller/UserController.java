package com.olx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olx.dto.AuthenticationRequest;
import com.olx.dto.User;
import com.olx.entity.BlacklistDocument;
import com.olx.repository.BlacklistedTokenRepo;
import com.olx.security.JwtUtil;
import com.olx.service.UserService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value="/olx/user")
public class UserController {
	
	
	// Injection authentication manager
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	BlacklistedTokenRepo blacklistTokenRepo;
	
	// 1 API - Login as a User
	@ApiOperation(value="Authenticate user in our olx-application")
	@PostMapping(value="/authenticate",consumes = MediaType.APPLICATION_JSON_VALUE)
	// Authenticate function takes argument of authentication request which is a pojo that holds info of username and password
	// Returns you auth-token of String type
	public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
		try {
			
			// Here we are calling the authenticate function on the manager explicitly programmatically.
			// It takes argument of authentication object
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken( 
							authenticationRequest.getUsername() , 
							authenticationRequest.getPassword()));
		}
		catch(BadCredentialsException ex) {
			// Showing error message to the user that authentication got failed
			return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		// Login successful .... now return auth-token
		String jwtToken = jwtUtil.generateTokenByUsername(authenticationRequest.getUsername());
		// return new ResponseEntity("A45B", HttpStatus.OK);
		return new ResponseEntity(jwtToken, HttpStatus.OK);
	}	
	
	
	
	
	// Validating of the token
	@GetMapping(value="/validate/token")
	@ApiOperation(value ="This REST endpoint to validate the user by its token")
	public ResponseEntity<Boolean> isValidateUser(@RequestHeader("Authorization")String authToken) {
		// Business logic steps
		// 1. Extract 'Bearer' word from the token using substring() method
		
		String jwtToken = authToken.substring(7, authToken.length());
		
		// 2. Validate the token using JwtUtil.validateToken(xxx) method
		// Validate token takes 2 objects - token and user details object
		// Extract username 
	
		String clientUsername = jwtUtil.extractUsername(jwtToken);
		
		// Here we want only username that is why we are getting only username
	
		String databaseUsername = userDetailsService.loadUserByUsername(clientUsername).getUsername();
		
		boolean isValidToken = jwtUtil.validateTokenByUsername(jwtToken, databaseUsername);
		// 3. Return result true/false to the client
	    if(isValidToken)
	    	return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	    else
	    	return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}
	
	
	
	// 2 API - Logouts a user
	@DeleteMapping(value="/logout")
	@ApiOperation(value ="This REST endpoint that deletes/logouts the user")
	public ResponseEntity<String> logout(@RequestHeader("auth-token") String authToken) {
		String token = authToken.substring(authToken.indexOf(' ') + 1);
		BlacklistDocument tokendoc = new BlacklistDocument();
		tokendoc.setToken(token);
		blacklistTokenRepo.save(tokendoc);
		return new ResponseEntity<String>("Ohhh yeahh !! Succesfully Logout done ..", HttpStatus.OK);
		//return new ResponseEntity<Boolean>(true,HttpStatus.OK);
	}	
	
	
	
	// 3 API - Creates a new user 
	@PostMapping(value="/",
			consumes= { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
			produces ={ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value ="This REST endpoint creates a user")
	public ResponseEntity<User> createNewUser(@RequestBody User user) throws Exception{
		log.info("Entering into User controller ...");
		return new ResponseEntity<User>(userService.createNewUser(user),HttpStatus.OK);
	}

	
	
	// 4 API - Gets user information based on auth token
	@GetMapping(value="/", 
			    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value ="This REST endpoint that returns user based on the auth token")
	public User getUser(@RequestHeader ("auth-token") String authToken){
		log.info("Getting the user information controller... ");
		System.out.println(authToken);
		return this.userService.getUser(authToken);
	}		
}

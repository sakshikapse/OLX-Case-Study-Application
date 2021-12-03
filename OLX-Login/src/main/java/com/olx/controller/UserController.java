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
import com.olx.security.JwtUtil;
import com.olx.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/olx")
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
	
	
	// 1 API - Login as a User
	@ApiOperation(value="Authenticate user in our olx-application")
	@PostMapping(value="/user/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
	// Authenticate function takes argument of authentication request which is a pojo that holds info of username and password
	// And that returns you auth-token
	// String is a "auth-token"
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
		// For time being I am returning hardcoded auth-token
		String jwtToken = jwtUtil.generateTokenByUsername(authenticationRequest.getUsername());
		// return new ResponseEntity("A45B", HttpStatus.OK);
		return new ResponseEntity(jwtToken, HttpStatus.OK);
	}	
	
	
	
	
	// Validating the token
	@GetMapping(value="/user/validate/token")
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
	
	
	
	// 3 API - Create new user 
	@PostMapping(value="/user",
			consumes= { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
			produces ={ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value ="This REST endpoint creating a user")
	public ResponseEntity<User> createNewUser(@RequestBody User user) throws Exception{
		return new ResponseEntity<User>(userService.createNewUser(user),HttpStatus.OK);
	}

	
	
	// 4 API - Gets user information based on auth token
	@GetMapping(value="/user", 
			    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value ="This REST endpoint that returns user based on the auth token")
	public User getUser(@RequestHeader ("auth-token") String authToken){
		System.out.println(authToken);
		return this.userService.getUser(authToken);
	}	
	

	
	// 2 API - Logouts a user
	// Here we are writing deleete mapping
	@DeleteMapping(value="/user/logout")
	// Writng a logout method
	public ResponseEntity<Boolean> logout(@RequestHeader("auth-token") String authToken) {
		// Writing a code for persisting the json web token into nosql db
		// We are putting it simply because tommorrow if I get request with this token I will go to the database and confirm that token which ...
		// ... is send by my client is not register in my blacklist token or not
		// Suppose you try to post a new advertisement so what will I do I will take your token and I am going to check whether that token is there or not into blacklisted token
		// If it is there I will throw the error to the user that you have to login first
		// Just pickup the token from the client and save it into a new collection called blacklist token
		
		// Now you will ask why you are going to NoSQL and what's wrong if I store those token into traditional mysql database
		// I will create one table blacklisted-token and I will save it what's wrong with it
		// There is nothing wrong you can save into mysql database also
		// Problem is that in the popular applications you are going to have million of users logged in
		// For normal applications 10,000 or more users have logged in simulataneously
		// if somebody has call logout you have to go through the blacklisted token list and it is a bulky table.
		// And that is why we go with in-memory databse that is nothing but nosql database
		// hence for token blacklisting of json web token traditionally we go for a no-sql databases
		// In SQL databases also we have 4 types - document based, key-value pairs, column based, graph based
		// Out of these 4 mongodb is a document based 
		// To have faster processing speed the key-value databases are always preferred
		// Hence token maintainence is taken care moslty by reddis database but we do not use reddis for the time being we go with mongodb
		// So for the timebeing we can impelemt the logout service you are going to store blacklisted token into mongodb collections.
		return new ResponseEntity<Boolean>(true,HttpStatus.OK);
	}
	
}

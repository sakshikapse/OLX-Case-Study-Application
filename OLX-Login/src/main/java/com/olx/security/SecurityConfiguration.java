package com.olx.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	
	public void configure(AuthenticationManagerBuilder auth) throws Exception {   // Override Authentication
        // Database authentication
		auth.userDetailsService(userDetailsService);
	}
	
	
	
	
	// Here we are only creating the object of the authentication manager
	// We will inject this bean into UserController by using autowiring feature.
	@Bean
	// Here I am getting the same authentication manager which my base class is currently holding.
	// Once i get authentication manager I can inject it anywhere 
	// I will get Authentication Manager from the base class Web Security Configurer Adapter
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
		
	
	
	@Override
	public void configure(HttpSecurity http) throws Exception{  // Overriding Authorization
		// I have to disable to call from some other application
		http.csrf().disable()
		.authorizeRequests()
        .antMatchers("/user/authenticate").permitAll()
        .and()
        .formLogin();           // Default Login page
	}
}

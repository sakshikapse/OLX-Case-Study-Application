package com.olx.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class SampleController {

	
	// To read the value of the property spring.datasource.url
	// To read the property we call the annotation - @Value and it reads any property which have configured in application.yml programmatically
	
	@Value("${spring.datasource.url}")
	String dbUrl;
	
	@GetMapping(value="/")
	// dbUrl is the property that I want to read the property of spring.datasource.url
	public String getDbUrl(){
		// Returning dbUrl to the client
		return "DB URL: " +this.dbUrl;
	}
}

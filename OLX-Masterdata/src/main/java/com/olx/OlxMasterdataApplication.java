package com.olx;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
@EnableEurekaClient
public class OlxMasterdataApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlxMasterdataApplication.class, args);
	}
	
	
	
	@Bean
	public Docket getCustomizeDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getApiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.olx"))
				.paths(PathSelectors.any())
				.build();
	}
	
	
	public ApiInfo getApiInfo() {
		return new ApiInfo(
				"OLX Master Data API Documentation",
				"OLX Master Data REST API documentation released by OLX Application",
		        "2.2",
		        "http://zensar.com",
		         new Contact("Sakshi","http://sakshik.com","sakshik@zensar.com"),
		         "GPL",
		         "http://gpl.com",
				 new ArrayList<VendorExtension>());
	}
	
	
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

}

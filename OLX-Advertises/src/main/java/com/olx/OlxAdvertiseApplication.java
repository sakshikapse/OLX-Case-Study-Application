package com.olx;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
@EnableEurekaClient
public class OlxAdvertiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlxAdvertiseApplication.class, args);
		System.out.println("Hello");
	}

	@Bean
	/* For direct intermicroservice call we can create ordinary REST TEMPLATE
	 * But whenever you want to establish intermicroservice communication through eureka server your rest templte object has to be smart object
	 * For the smart object you have to put annotation called @LoadBalanced */
    
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
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
				"OLX Advertise Data API Documentation",
				"OLX Advertise Data REST API documentation released by OLX Application",
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
package com.olx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class OlxEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlxEurekaServerApplication.class, args);
		System.out.println("Hey!! Your Eureka server has been started");
	}

}

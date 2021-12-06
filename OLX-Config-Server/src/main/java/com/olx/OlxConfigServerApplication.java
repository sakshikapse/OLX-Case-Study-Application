package com.olx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class OlxConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlxConfigServerApplication.class, args);
		System.out.println("Config server has been successfully started !!!");
	}
}

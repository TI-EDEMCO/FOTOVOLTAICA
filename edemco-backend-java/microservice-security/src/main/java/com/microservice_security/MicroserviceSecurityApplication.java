package com.microservice_security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceSecurityApplication.class, args);
	}

}

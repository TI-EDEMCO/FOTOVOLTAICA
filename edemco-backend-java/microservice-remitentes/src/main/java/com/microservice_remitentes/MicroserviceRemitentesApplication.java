package com.microservice_remitentes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceRemitentesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceRemitentesApplication.class, args);
	}

}

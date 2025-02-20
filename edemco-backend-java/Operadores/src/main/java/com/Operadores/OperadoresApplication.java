package com.Operadores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class OperadoresApplication {

	public static void main(String[] args) {
		SpringApplication.run(OperadoresApplication.class, args);
	}

}

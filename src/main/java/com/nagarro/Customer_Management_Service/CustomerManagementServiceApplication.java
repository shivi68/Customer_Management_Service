package com.nagarro.Customer_Management_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class CustomerManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerManagementServiceApplication.class, args);
	}

	//used for making HTTP requests
		@Bean
		
		public WebClient webClient() {
			// Create and return a WebClient instance
			return WebClient.builder().build();
		}
}

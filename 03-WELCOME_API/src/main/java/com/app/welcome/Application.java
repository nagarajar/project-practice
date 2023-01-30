package com.app.welcome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	private Logger logger = LoggerFactory.getLogger(Application.class);
	
	@GetMapping("/welcome")
	public String welcomeMsg() {
		logger.info("welcomeMsg() execution - start");
		String welcomeMsg = "Welcome to WELCOME_API application";
		logger.info("welcomeMsg() execution - end ");
		return welcomeMsg;
	}

}

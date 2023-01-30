package com.app.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.user.client.WelcomeClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Autowired
	private WelcomeClient welcomeClient;
	
	private Logger logger = LoggerFactory.getLogger(Application.class);

	@GetMapping("/user")
	public String welcomeUserMsg() {
		
		logger.info("welcomeUserMsg() execution - start");
		
		String userApiMsg = "Welcome to USER_API application";
		
		String welcomeApiMsg = welcomeClient.invokeWelcomeMsg();
		
		logger.info("welcomeUserMsg() execution - end ");
		
		return userApiMsg+" and "+ welcomeApiMsg;
	}
}

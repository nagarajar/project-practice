package com.ar.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.ar.api.binding.RegForm;
import com.ar.api.entity.Citizen;
import com.ar.api.repository.CitizenRepository;

@Service
public class CitizenServiceImpl implements ICitizenService {

	@Autowired
	private CitizenRepository citizenRepository;

	@Override
	public String register(RegForm regForm) {
		// 1. Create URL
		String url = "http://ssawebapi-env.eba-k88bsahp.ap-south-1.elasticbeanstalk.com/ssn/{ssn}";

		// 2. Create RestTemplate object/ Create a WebClient
		//RestTemplate template = new RestTemplate();

		WebClient webClient = WebClient.create();
		
		// 3. Make HTTP call and Get Response back
		// Using RestTemple - old way 
		// ResponseEntity<String> response = template.getForEntity(url, String.class, regForm.getSsn() // path variables
		//);
		
		// Using WebClient
		String stateName = webClient.get()							//Represents get method
									.uri(url, regForm.getSsn())		//Specify url to hit
									.retrieve()						//Extract response 
									.bodyToMono(String.class)		//Bind response
									.block();						//Synchronous Call 
		
		//System.out.println(response.getBody());
		System.out.println(stateName);
		
		//if(response.getBody().equalsIgnoreCase("Rhode Island")) {
		if("Rhode Island".equalsIgnoreCase(stateName)) {
			// 1. Copy the data from binding obj to entity obj
			Citizen citizen = new Citizen();
			BeanUtils.copyProperties(regForm, citizen);
			// 2. Save the entity
			citizen = citizenRepository.save(citizen);
			
			return "Citizen Registered Succefully with App ID : "+citizen.getAppId();
		}
		else
		{	/*
			if(response.getBody().equalsIgnoreCase("Invalid SSN"))
				return response.getBody();
			return response.getBody()+" is not belongs to RI States";*/
			
			if("Invalid SSN".equalsIgnoreCase(stateName))
				return stateName;
			return stateName+" is not belongs to RI States";
		}
		
	}

}

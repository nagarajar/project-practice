package com.ar.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

		// 2. Create RestTemplate object
		RestTemplate template = new RestTemplate();

		// 3. Make HTTP call and Get Response back
		ResponseEntity<String> response = template.getForEntity(url, String.class, regForm.getSsn() // path variables
		);
		
		System.out.println(response.getBody());
		
		if(response.getBody().equalsIgnoreCase("Rhode Island")) {
			// 1. Copy the data from binding obj to entity obj
			Citizen citizen = new Citizen();
			BeanUtils.copyProperties(regForm, citizen);
			// 2. Save the entity
			citizenRepository.save(citizen);
			
			return "Registered Succefully";
		}
		else
		{
			if(response.getBody().equalsIgnoreCase("Invalid SSN"))
				return response.getBody();
			return response.getBody()+" is not belongs to RI States";
		}
		
	}

}

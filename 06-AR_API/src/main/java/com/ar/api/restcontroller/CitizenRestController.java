package com.ar.api.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ar.api.binding.RegForm;
import com.ar.api.service.ICitizenService;

@RestController
@RequestMapping("/citizen")
public class CitizenRestController {

	@Autowired
	private ICitizenService service;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegForm regForm){
		String regMsg = service.register(regForm);	
		if(regMsg.equalsIgnoreCase("Registered Succefully")) {			
			return new ResponseEntity<String>(regMsg, HttpStatus.CREATED);
		}
		return new ResponseEntity<String>(regMsg, HttpStatus.OK);
	}
}

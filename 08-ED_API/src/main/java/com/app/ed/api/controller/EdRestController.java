package com.app.ed.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.app.ed.api.binding.EligResponse;
import com.app.ed.api.service.IEdService;

@RestController
public class EdRestController {

	@Autowired
	private IEdService edService;
	
	@GetMapping("/eligibility/{caseNum}")
	public ResponseEntity<EligResponse> determine(@PathVariable Integer caseNum){
		EligResponse eligResponse = edService.determineEligibility(caseNum);
		return new ResponseEntity<>(eligResponse, HttpStatus.OK	);
	}
}

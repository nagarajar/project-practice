package com.app.bi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bi.api.service.IBiService;

@RestController
@RequestMapping("/bi-api")
public class BiRestController {

	@Autowired
	private IBiService biService;
	
	@GetMapping("/generate")
	public ResponseEntity<String> generateBi(){
		String info = biService.generateBiInfo();
		return ResponseEntity.ok(info);
	}
}

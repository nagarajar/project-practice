package com.app.co.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.co.api.entity.CoBatchResponseEntity;
import com.app.co.api.service.ICoService;

@RestController
@RequestMapping("/co-api")
public class CoRestController {

	@Autowired
	private ICoService coService;
	
	@GetMapping("/generateCo")
	public ResponseEntity<CoBatchResponseEntity> generateCoReport(){
		CoBatchResponseEntity coBatchResponseEntity = coService.processPendingTriggers();
		return ResponseEntity.ok(coBatchResponseEntity);
	}
}

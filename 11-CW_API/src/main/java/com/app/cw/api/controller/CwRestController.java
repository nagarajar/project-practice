package com.app.cw.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.cw.api.binding.DashboardResponse;
import com.app.cw.api.binding.LoginForm;
import com.app.cw.api.binding.UnlockAccForm;
import com.app.cw.api.entity.CwEntity;
import com.app.cw.api.service.ICwService;

@RestController
@RequestMapping("/cw-api")
public class CwRestController {

	@Autowired
	private ICwService cwService;
	
	@PostMapping("/unlock")
	public ResponseEntity<String> unlockAccount(@RequestBody UnlockAccForm unlockAccForm){
		String status = cwService.unlockAccount(unlockAccForm);
		return ResponseEntity.ok(status);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginForm loginForm){
		String status = cwService.login(loginForm);
		return ResponseEntity.ok(status);
	}

	@GetMapping("/forgotpwd/{email}")
	public ResponseEntity<String> forgotPwd(@PathVariable String email){ 
		String status = cwService.forgotPwd(email);
		return ResponseEntity.ok(status);
	}
	
	@PutMapping("/update")
	public ResponseEntity<CwEntity> updateProfile(@RequestBody CwEntity cwRequest){
		CwEntity cwEntity = cwService.updateProfile(cwRequest);
		return ResponseEntity.ok(cwEntity);
	}
	
	@GetMapping("/dashboard")
	public ResponseEntity<DashboardResponse> dashboardDisply(){ 
		DashboardResponse response = cwService.dashboardDisply();
		return ResponseEntity.ok(response);
	}
}

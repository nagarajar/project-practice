package com.app.dc.api.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.dc.api.binding.EducationDetailsForm;
import com.app.dc.api.binding.IncomeDetailsForm;
import com.app.dc.api.binding.KidsDetailsForm;
import com.app.dc.api.binding.PlanForm;
import com.app.dc.api.entity.Plan;
import com.app.dc.api.service.IDCApiService;

@RestController
public class DCApiRestController {
	
	@Autowired
	private IDCApiService idcApiService;
	
	@GetMapping("/search/{appId}")
	public ResponseEntity<String> searchAppId(@PathVariable Integer appId){
		String msg = idcApiService.createCaseId(appId);
		if("Not Found".equalsIgnoreCase(msg)) {			
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}
		return new ResponseEntity<String>(msg,HttpStatus.CREATED);
	}
	
	@PostMapping("/selectPlan")
	public ResponseEntity<String> selectPlan(@RequestBody PlanForm planForm){
		String msg = idcApiService.selectPlan(planForm);
		return ResponseEntity.ok(msg);
	}
	
	@PostMapping("/saveIncomeDetails")
	public ResponseEntity<String> saveIncomeDetails(@RequestBody IncomeDetailsForm incomeDetailsForm){
		String msg = idcApiService.saveIncomeDetails(incomeDetailsForm);
		return ResponseEntity.ok(msg);
	}
	
	@PostMapping("/saveEducationDetails")
	public ResponseEntity<String> saveEducationDetails(@RequestBody EducationDetailsForm educationDetailsForm){
		String msg = idcApiService.saveEducationDetails(educationDetailsForm);
		return ResponseEntity.ok(msg);
	}
	
	@PostMapping("/saveKidsDetails")
	public ResponseEntity<String> saveKidsDetails(@RequestBody KidsDetailsForm kidsDetailsForm){
		String msg = idcApiService.saveKidsDetails(kidsDetailsForm);
		return ResponseEntity.ok(msg);
	}
	
	
	@GetMapping("/plan/{planId}")
	public ResponseEntity<Plan> getPlan(@PathVariable Integer planId){
		Plan plan = idcApiService.getPlanById(planId);		
		return ResponseEntity.ok(plan);
	}
	
	@DeleteMapping("/plan/{planId}")
	public ResponseEntity<String> deletePlan(@PathVariable Integer planId){
		String msg = idcApiService.deletePlan(planId);		
		return ResponseEntity.ok(msg);
	}
	
	@GetMapping("/plans")
	public ResponseEntity<List<Plan>> getAllPlans(){
		List<Plan> plans = idcApiService.getAllPlans();		
		return ResponseEntity.ok(plans);
	}
	
	@PostMapping("/plan/add")
	public ResponseEntity<String> savePlan(@RequestBody Plan plan){
		String msg = idcApiService.addPlan(plan);
		return ResponseEntity.ok(msg);
	}
	
	@PostMapping("/plan/update")
	public ResponseEntity<String> updatePlan(@RequestBody Plan plan){
		String msg = idcApiService.updatePlan(plan);
		return ResponseEntity.ok(msg);
	}
	

}

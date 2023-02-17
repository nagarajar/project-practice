package com.app.admin.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.admin.api.binding.PlanRequest;
import com.app.admin.api.entity.PlanEntity;
import com.app.admin.api.service.IPlanService;

@RestController
@RequestMapping("/cw-api/plan")
public class PlanRestController {

	@Autowired
	private IPlanService planService;
	
	@PostMapping("/save")
	public ResponseEntity<String> createPlan(@RequestBody PlanRequest planRequest){
		Integer planId = planService.createPlan(planRequest);
		if(planId != null) {
			return new ResponseEntity<>("Saved",HttpStatus.CREATED);
		}
		return new ResponseEntity<>("Failed to save",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/{planId}")
	public ResponseEntity<?> getPlanById(@PathVariable Integer planId){
		PlanEntity planEntity = planService.findByPlanId(planId);
		if(planEntity != null) {
			return new ResponseEntity<>(planEntity,HttpStatus.OK);
		}
		return new ResponseEntity<>(planId +" Not Found",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllPlans(){
		List<PlanEntity> allPlans = planService.getAllPlans();
		if(allPlans != null) {
			return new ResponseEntity<>(allPlans,HttpStatus.OK);
		}
		return new ResponseEntity<>("Failed to retrive data",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updatePlan(@RequestBody PlanEntity plan){
		PlanEntity planEntity = planService.updatePlan(plan);
		if(planEntity != null) {
			return new ResponseEntity<>(planEntity,HttpStatus.OK);
		}
		return new ResponseEntity<>("Failed to update",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("active-deactive/{planId}")
	public ResponseEntity<?> activateOrDeactivatePlan(@PathVariable Integer planId){
		String msg = planService.activateOrDeactivatePlan(planId);
		if(msg != null) {
			return new ResponseEntity<>(msg,HttpStatus.OK);
		}
		return new ResponseEntity<>(planId +" Not Found",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping("/{planId}")
	public ResponseEntity<?> deleteByPlanId(@PathVariable Integer planId){
		String msg = planService.deleteByPlanId(planId);
		if(msg != null) {
			return new ResponseEntity<>(msg,HttpStatus.OK);
		}
		return new ResponseEntity<>(planId +" Not Found",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}

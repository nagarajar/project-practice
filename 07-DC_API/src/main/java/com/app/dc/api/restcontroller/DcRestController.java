package com.app.dc.api.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.dc.api.binding.Education;
import com.app.dc.api.binding.Income;
import com.app.dc.api.binding.KidsInfo;
import com.app.dc.api.binding.PlanSelection;
import com.app.dc.api.binding.Summary;
import com.app.dc.api.service.IDcService;

@RestController
public class DcRestController {
	
	@Autowired
	private IDcService idcService;
	
	@GetMapping("/create/case/{appId}")
	public ResponseEntity<?> createCase(@PathVariable Integer appId){
		PlanSelection planSelection = idcService.createCase(appId);
		if(planSelection == null) {			
			return new ResponseEntity<String>("Invalid App ID",HttpStatus.OK);
		}
		return new ResponseEntity<>(planSelection,HttpStatus.CREATED);
	}
	
	@PostMapping("/selectPlan")
	public ResponseEntity<Integer> selectPlan(@RequestBody PlanSelection planSelection){
		 Integer caseId = idcService.selectCitizenPlan(planSelection);
		return ResponseEntity.ok(caseId);
	}
	
	@PostMapping("/saveIncomeDetails")
	public ResponseEntity<Integer> saveIncomeDetails(@RequestBody Income income){
		 Integer caseId = idcService.saveIncomeDetails(income);
		return ResponseEntity.ok(caseId);
	}
	
	@PostMapping("/saveEducationDetails")
	public ResponseEntity<Integer> saveEducationDetails(@RequestBody Education education){
		Integer caseId = idcService.saveEducationDetails(education);
		return ResponseEntity.ok(caseId);
	}
	
	@PostMapping("/saveKidsDetails")
	public ResponseEntity<Summary> saveKidsDetails(@RequestBody KidsInfo kidsInfo){
		Summary summary = idcService.saveKidsDetails(kidsInfo);
		return ResponseEntity.ok(summary);
	}
	
	@GetMapping("/summary/{caseId}")
	public ResponseEntity<Summary> saveKidsDetails(@PathVariable Integer caseId){
		Summary summary = idcService.getSummary(caseId);
		return ResponseEntity.ok(summary);
	}	

}

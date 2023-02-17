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

import com.app.admin.api.binding.CwRequest;
import com.app.admin.api.binding.CwResponse;
import com.app.admin.api.entity.CwEntity;
import com.app.admin.api.service.ICwService;

@RestController
@RequestMapping("/cw-api/cw")
public class CwRestController {

	@Autowired
	private ICwService cwService;
	
	@PostMapping("/save")
	public ResponseEntity<String> createCw(@RequestBody CwRequest cwRequest){
		Integer cwId = cwService.createCw(cwRequest);
		if(cwId != null) {
			return new ResponseEntity<>("Saved",HttpStatus.CREATED);
		}
		return new ResponseEntity<>("Failed to save",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/{cwId}")
	public ResponseEntity<?> getById(@PathVariable Integer cwId){
		CwResponse cwResponse = cwService.findByCwId(cwId);
		if(cwResponse != null) {
			return new ResponseEntity<>(cwResponse,HttpStatus.OK);
		}
		return new ResponseEntity<>(cwId +" Not Found",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllCws(){
		List<CwEntity> allCws = cwService.getAllCws();
		if(allCws != null) {
			return new ResponseEntity<>(allCws,HttpStatus.OK);
		}
		return new ResponseEntity<>("Failed to retrive data",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> getById(@RequestBody CwResponse response){
		CwEntity cwEntity = cwService.updateCw(response);
		if(cwEntity != null) {
			return new ResponseEntity<>(cwEntity,HttpStatus.OK);
		}
		return new ResponseEntity<>("Failed to update",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("active-deactive/{cwId}")
	public ResponseEntity<?> activateOrDeactivateCw(@PathVariable Integer cwId){
		String msg = cwService.activateOrDeactivateCw(cwId);
		if(msg != null) {
			return new ResponseEntity<>(msg,HttpStatus.OK);
		}
		return new ResponseEntity<>(cwId +" Not Found",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping("/{cwId}")
	public ResponseEntity<?> deleteByCwId(@PathVariable Integer cwId){
		String msg = cwService.deleteByCwId(cwId);
		if(msg != null) {
			return new ResponseEntity<>(msg,HttpStatus.OK);
		}
		return new ResponseEntity<>(cwId +" Not Found",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}

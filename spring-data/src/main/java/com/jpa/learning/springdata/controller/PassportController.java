package com.jpa.learning.springdata.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.learning.springdata.resourceObject.PassportRequest;
import com.jpa.learning.springdata.resourceObject.PassportResponse;
import com.jpa.learning.springdata.service.PassportService;

@RestController
public class PassportController {

	@Autowired
	private PassportService service;

	@GetMapping("/getAllPassports")
	public ResponseEntity<List<PassportResponse>> getAllPassportDetails() {
		return ResponseEntity.ok().body(service.getAllPassportDetails());
	}
	
	@PostMapping(path = "/addOrUpdatePassport", consumes = "application/json", produces = "application/json")
	public ResponseEntity<PassportResponse> addorUpdatePassport(@Valid @RequestBody PassportRequest request){
		return ResponseEntity.ok().body(service.addorUpdatePassport(request));
	}
	
	@DeleteMapping("/deletePassport")
	public String deletePassport(Long courseId) {
		return service.deletePassport(courseId);
	}
}

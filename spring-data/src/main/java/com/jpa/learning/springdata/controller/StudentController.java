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

import com.jpa.learning.springdata.resourceObject.StudentRequest;
import com.jpa.learning.springdata.resourceObject.StudentResponse;
import com.jpa.learning.springdata.service.StudentService;

@RestController
public class StudentController {

	@Autowired
	private StudentService service;

	@GetMapping("/getAllStudents")
	public ResponseEntity<List<StudentResponse>> getAllStudentDetails() {
		return ResponseEntity.ok().body(service.getAllStudentDetails());
	}
	
	@GetMapping("/getStudentWithPassport")
	public ResponseEntity<StudentResponse> getStudentWithPassport(Long id) {
		return ResponseEntity.ok().body(service.getStudentWithPassport(id));
	}
	
	@PostMapping(path = "/addOrUpdateStudent", consumes = "application/json", produces = "application/json")
	public ResponseEntity<StudentResponse> addorUpdateStudent(@Valid @RequestBody StudentRequest request){
		return ResponseEntity.ok().body(service.addorUpdateStudent(request));
	}
	
	@DeleteMapping("/deleteStudent")
	public String deleteStudent(Long studentId) {
		return service.deleteStudent(studentId);
	}
}

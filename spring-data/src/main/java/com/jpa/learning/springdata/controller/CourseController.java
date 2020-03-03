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

import com.jpa.learning.springdata.resourceObject.CourseRequest;
import com.jpa.learning.springdata.resourceObject.CourseResponse;
import com.jpa.learning.springdata.service.CourseService;

@RestController
public class CourseController {

	@Autowired
	private CourseService service;

	@GetMapping("/getAllCourses")
	public ResponseEntity<List<CourseResponse>> getAllCourseDetails() {
		return ResponseEntity.ok().body(service.getAllCourseDetails());
	}
	
	@PostMapping(path = "/addOrUpdate", consumes = "application/json", produces = "application/json")
	public ResponseEntity<CourseResponse> addorUpdateCourse(@Valid @RequestBody CourseRequest request){
		return ResponseEntity.ok().body(service.addorUpdateCourse(request));
	}
	
	@DeleteMapping("/deleteCourse")
	public String deleteCourse(Long courseId) {
		return service.deleteCourse(courseId);
	}
}

package com.jpa.learning.springdata.service;

import java.util.List;

import com.jpa.learning.springdata.resourceObject.StudentRequest;
import com.jpa.learning.springdata.resourceObject.StudentResponse;

public interface StudentService {
	public List<StudentResponse> getAllStudentDetails();

	public StudentResponse addorUpdateStudent(StudentRequest request);

	public String deleteStudent(Long studentId);
	
	public StudentResponse getStudentWithPassport(Long id);
}

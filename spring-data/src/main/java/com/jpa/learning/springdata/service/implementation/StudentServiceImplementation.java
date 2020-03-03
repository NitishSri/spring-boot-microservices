package com.jpa.learning.springdata.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.learning.springdata.repository.StudentRepository;
import com.jpa.learning.springdata.resourceObject.StudentRequest;
import com.jpa.learning.springdata.resourceObject.StudentResponse;
import com.jpa.learning.springdata.service.StudentService;

@Service
public class StudentServiceImplementation implements StudentService {

	@Autowired
	StudentRepository repository;

	@Override
	public List<StudentResponse> getAllStudentDetails() {
		return repository.getAllStudents();
	}

	@Override
	public StudentResponse addorUpdateStudent(StudentRequest request) {
		return repository.addorUpdateStudent(request);
	}

	@Override
	public String deleteStudent(Long studentId) {
		return repository.deleteStudent(studentId);
	}

	@Override
	public StudentResponse getStudentWithPassport(Long id) {
		return repository.getStudentWithPassport(id);
	}

}

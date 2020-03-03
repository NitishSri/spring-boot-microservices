package com.jpa.learning.springdata.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jpa.learning.springdata.model.Passport;
import com.jpa.learning.springdata.model.Student;
import com.jpa.learning.springdata.resourceObject.StudentRequest;
import com.jpa.learning.springdata.resourceObject.StudentResponse;

@Repository
@Transactional
public class StudentRepository {

	@Autowired
	EntityManager em;

	public List<StudentResponse> getAllStudents() {
		List<Student> students = em.createNamedQuery("get_all_students").getResultList();
		ModelMapper modelMapper = new ModelMapper();
		List<StudentResponse> list = Arrays.asList(modelMapper.map(students, StudentResponse[].class));
		return list;
	}

	public StudentResponse addorUpdateStudent(StudentRequest request) {
		Student student = null != request.getId() ? em.find(Student.class, request.getId()) : null;
		ModelMapper modelMapper = new ModelMapper();
		Student studentEntity = modelMapper.map(request, Student.class);
		if (Objects.isNull(student)) {
			Passport passport = request.getPassport();
			em.persist(passport);
			studentEntity.setPassport(passport);
			em.persist(studentEntity);
		} else {
			Long passportId = student.getPassport().getId();
			Passport passport = em.find(Passport.class, passportId);
			passport.setNumber(request.getPassport().getNumber());
			em.merge(passport);
			studentEntity.setPassport(passport);
			em.merge(studentEntity);
		}
		return modelMapper.map(studentEntity, StudentResponse.class);
	}

	public String deleteStudent(Long id) {
		Student student = em.find(Student.class, id);
		String message = Strings.EMPTY;
		if (Objects.isNull(student)) {
			message = "No student with this Id found to delete !!";
		} else {
			em.remove(student);
			message = "Student with given id is deleted : " + id;
		}

		return message;
	}

	public StudentResponse getStudentWithPassport(Long id) {
		Student student = em.find(Student.class, id);
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(student, StudentResponse.class);
	}

}

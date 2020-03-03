package com.jpa.learning.springdata.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.learning.springdata.repository.CourseRepository;
import com.jpa.learning.springdata.resourceObject.CourseRequest;
import com.jpa.learning.springdata.resourceObject.CourseResponse;
import com.jpa.learning.springdata.service.CourseService;

@Service
public class CourseServiceImplementation implements CourseService {

	@Autowired
	CourseRepository repository;

	@Override
	public List<CourseResponse> getAllCourseDetails() {
		return repository.getAllCourses();
	}

	@Override
	public CourseResponse addorUpdateCourse(CourseRequest request) {
		return repository.addorUpdateCourse(request);
	}

	@Override
	public String deleteCourse(Long courseId) {
		return repository.deleteCourse(courseId);
	}

}

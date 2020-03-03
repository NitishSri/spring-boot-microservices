package com.jpa.learning.springdata.service;

import java.util.List;

import com.jpa.learning.springdata.resourceObject.CourseRequest;
import com.jpa.learning.springdata.resourceObject.CourseResponse;

public interface CourseService {

	public List<CourseResponse> getAllCourseDetails();
	
	public CourseResponse addorUpdateCourse(CourseRequest request);
	
	public String deleteCourse(Long courseId);

}

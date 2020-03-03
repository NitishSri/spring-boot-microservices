package com.jpa.learning.springdata.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jpa.learning.springdata.model.Course;
import com.jpa.learning.springdata.resourceObject.CourseRequest;
import com.jpa.learning.springdata.resourceObject.CourseResponse;

@Repository
@Transactional
public class CourseRepository {

	@Autowired
	EntityManager em;

	public List<CourseResponse> getAllCourses() {
		List<Course> courses = em.createNamedQuery("get_all_courses").getResultList();
		ModelMapper modelMapper = new ModelMapper();
		List<CourseResponse> list = Arrays.asList(modelMapper.map(courses, CourseResponse[].class));
		return list;
	}

	public CourseResponse addorUpdateCourse(CourseRequest request) {
		Course course = null != request.getId() ? em.find(Course.class, request.getId()) : null;
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new PropertyMap<Course, CourseRequest>() {
			@Override
			protected void configure() {
				skip(destination.getCreatedDate());
			}
		});
		Course courseEntity = modelMapper.map(request, Course.class);
		// This means it is new course entry so save it using persist.
		if (Objects.isNull(course)) {
			em.persist(courseEntity);
		} else {
			// This means it is an existing entry so update it using merge.
			em.merge(courseEntity);
		}
		return modelMapper.map(courseEntity, CourseResponse.class);
	}

	public String deleteCourse(Long id) {
		Course course = em.find(Course.class, id);
		String message = Strings.EMPTY;
		if (Objects.isNull(course)) {
			message = "No course with this Id found to delete !!";
		} else {
			em.remove(course);
			message = "Course with given id is deleted : " + id;
		}

		return message;
	}

}

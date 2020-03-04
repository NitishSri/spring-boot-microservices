package com.jpa.learning.springdata.resourceObject;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.jpa.learning.springdata.model.Reviews;

public class CourseRequest {

	private Long Id;

	@NotNull(message = "Course name cannot be null")
	private String name;

	private LocalDateTime createdDate;

	private LocalDateTime updateDate;

	private List<Reviews> reviews;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public List<Reviews> getReviews() {
		return reviews;
	}

	public void setReviews(List<Reviews> reviews) {
		this.reviews = reviews;
	}

}

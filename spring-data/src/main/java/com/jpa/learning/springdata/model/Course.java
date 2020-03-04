package com.jpa.learning.springdata.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CourseDetails")
@NamedQueries(value = { @NamedQuery(name = "get_all_courses", query = "select c from Course c") })
public class Course {

	@GeneratedValue
	@Id
	private Long Id;

	@Column(name = "courseName", nullable = false)
	private String name;

	@CreationTimestamp
	private LocalDateTime createdDate;
	@UpdateTimestamp
	private LocalDateTime updateDate;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
	@JsonIgnore
	private List<Reviews> reviews = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Long getId() {
		return Id;
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

	public void addReviews(Reviews review) {
		this.reviews.add(review);
	}

	public void removeReviews(Reviews review) {
		this.reviews.remove(review);
	}

}

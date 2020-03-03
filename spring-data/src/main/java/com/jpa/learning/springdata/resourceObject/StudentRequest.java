package com.jpa.learning.springdata.resourceObject;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.jpa.learning.springdata.model.Passport;

public class StudentRequest {
	private Long Id;

	@NotNull(message = "Student name cannot be null")
	private String name;

	private LocalDateTime createdDate;

	private LocalDateTime updateDate;

	private Passport passport;

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

	public Passport getPassport() {
		return passport;
	}

	public void setPassport(Passport passport) {
		this.passport = passport;
	}

}

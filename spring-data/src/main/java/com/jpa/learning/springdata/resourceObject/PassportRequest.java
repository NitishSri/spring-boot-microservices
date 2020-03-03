package com.jpa.learning.springdata.resourceObject;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class PassportRequest {
	private Long Id;

	@NotNull(message = "Passport number cannot be null")
	private String number;

	private LocalDateTime createdDate;

	private LocalDateTime updateDate;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

}

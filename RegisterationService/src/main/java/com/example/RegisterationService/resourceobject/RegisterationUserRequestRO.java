package com.example.RegisterationService.resourceobject;

import javax.validation.constraints.NotNull;

public class RegisterationUserRequestRO {

	@NotNull(message = "username cannot be null")
	private String username;

	@NotNull(message = "Firstname cannot be null")
	private String firstname;

	@NotNull(message = "Lastname cannot be null")
	private String lastname;

	@NotNull(message = "Password cannot be null")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

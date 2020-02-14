package com.example.resourceobject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginRequestRO {

	@NotNull(message = "username cannot be null")
	private String username;

	@NotNull(message = "Password cannot be null")
	@Size(min = 4, max = 16, message = "Password must be equal or grater than 4 characters and less than 16 characters")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

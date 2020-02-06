package com.comparer.sendemailservice.resourceobject;

import java.io.Serializable;

public class ForgotPasswordRO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6771686426184102733L;
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

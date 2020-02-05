package com.example.service;

import com.example.model.LoginCredentials;

public interface DataService {

	public boolean checkUserExist(String username, String password);
	public LoginCredentials addUser(String username, String password);
	public boolean checkUsernameTaken(String username);

}

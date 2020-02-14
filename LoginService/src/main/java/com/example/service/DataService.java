package com.example.service;

import com.example.model.LoginCredentials;
import com.example.resourceobject.LoginResponseRO;

public interface DataService {

	public LoginResponseRO checkUserExist(String username);

	public LoginCredentials addUser(String username, String password);

	public boolean checkUsernameTaken(String username);

}

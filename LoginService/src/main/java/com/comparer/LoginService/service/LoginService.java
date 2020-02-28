package com.comparer.LoginService.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.comparer.LoginService.model.LoginCredentials;

public interface LoginService extends UserDetailsService{

	//public LoginResponseRO checkUserExist(String username);

	public LoginCredentials addUser(String username, String password);

	public boolean checkUsernameTaken(String username);

}

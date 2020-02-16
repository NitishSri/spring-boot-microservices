package com.comparer.LoginService.service.implementation;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.comparer.LoginService.model.LoginCredentials;
import com.comparer.LoginService.repository.LoginRepository;
import com.comparer.LoginService.service.DataService;

@Service
public class DataServiceImpl implements DataService   {

	@Autowired
	private LoginRepository loginRepository;

	@Override
	public LoginCredentials addUser(String username, String password) {
		LoginCredentials loginUser = new LoginCredentials();
		loginUser.setUsername(username);
		loginUser.setPassword(password);
		return loginRepository.save(loginUser);
	}

	@Override
	public boolean checkUsernameTaken(String username) {
		UserDetails user = loadUserByUsername(username);
		if (null!=user) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<LoginCredentials> credList = loginRepository.findByUsername(username);
		if (credList.isPresent()) {
			UserDetails user = new org.springframework.security.core.userdetails.User(credList.get().getUsername(), credList.get().getPassword(), new ArrayList<>());
			return user;
		}
		return null;
	}

}

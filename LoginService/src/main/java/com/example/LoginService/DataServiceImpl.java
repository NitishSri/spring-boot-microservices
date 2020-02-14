package com.example.LoginService;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.LoginCredentials;
import com.example.repository.LoginRepository;
import com.example.resourceobject.LoginResponseRO;
import com.example.service.DataService;

@Service
public class DataServiceImpl implements DataService   {

	@Autowired
	private LoginRepository loginRepository;

	@Override
	public LoginResponseRO checkUserExist(String username) {
		Optional<LoginCredentials> credList = loginRepository.findByUsername(username);
		if (credList.isPresent()) {
			return new ModelMapper().map(credList.get(), LoginResponseRO.class);
		}
		return null;
	}

	@Override
	public LoginCredentials addUser(String username, String password) {
		LoginCredentials loginUser = new LoginCredentials();
		loginUser.setUsername(username);
		loginUser.setPassword(password);
		return loginRepository.save(loginUser);
	}

	@Override
	public boolean checkUsernameTaken(String username) {
		Optional<LoginCredentials> userCred = loginRepository.findByUsername(username);
		if (userCred.isPresent()) {
			return true;
		} else {
			return false;
		}

	}

}

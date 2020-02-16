package com.example.RegisterationService.service.implementation;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RegisterationService.model.RegisterationUserInfo;
import com.example.RegisterationService.repository.RegistrationRepository;
import com.example.RegisterationService.resourceobject.RegisterationUserRequestRO;
import com.example.RegisterationService.resourceobject.RegisterationUserResponseRO;
import com.example.RegisterationService.service.UserRegistrationService;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

	@Autowired
	RegistrationRepository repository;

	@Override
	public RegisterationUserResponseRO saveUser(RegisterationUserRequestRO user) {
		ModelMapper modelMapper = new ModelMapper();
		RegisterationUserInfo userInfo = modelMapper.map(user, RegisterationUserInfo.class);
		repository.save(userInfo);
		RegisterationUserResponseRO returnValue = modelMapper.map(userInfo, RegisterationUserResponseRO.class);
		return returnValue;
	}

	@Override
	public boolean checkUsernameTaken(String username) {
		Optional<RegisterationUserInfo> user = repository.findByUsername(username);
		if (user.isPresent()) {
			return true;
		}
		return false;
	}

}

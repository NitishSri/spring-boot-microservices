package com.example.RegisterationService.service.implementation;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.RegisterationService.model.RegisterationUserInfo;
import com.example.RegisterationService.repository.RegistrationRepository;
import com.example.RegisterationService.resourceobject.RegisterationUserRequestRO;
import com.example.RegisterationService.resourceobject.RegisterationUserResponseRO;
import com.example.RegisterationService.resourceobject.UserDetailsRO;
import com.example.RegisterationService.service.UserRegistrationService;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

	@Autowired
	RegistrationRepository repository;

	@Autowired
	CacheManager cacheManager;

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

	@Cacheable("user")
	@Override
	public UserDetailsRO getUserDetails(String username) {
		Optional<RegisterationUserInfo> user = repository.findByUsername(username);
		if (user.isPresent()) {
			return new ModelMapper().map(user.get(), UserDetailsRO.class);
		} else {
			return null;
		}
	}

	@Override
	public UserDetailsRO updateUserDetails(UserDetailsRO userDetails) {
		RegisterationUserInfo userInfo = new RegisterationUserInfo();
		userInfo.setId(userDetails.getId());
		userInfo.setUsername(userDetails.getUsername());
		userInfo.setFirstname(userDetails.getFirstname());
		userInfo.setLastname(userDetails.getLastname());
		repository.save(userInfo);
		cacheManager.getCache("user").clear();
		return new ModelMapper().map(userInfo, UserDetailsRO.class);
	}

}

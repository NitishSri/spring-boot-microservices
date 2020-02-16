package com.example.RegisterationService.service;

import com.example.RegisterationService.resourceobject.RegisterationUserRequestRO;
import com.example.RegisterationService.resourceobject.RegisterationUserResponseRO;

public interface UserRegistrationService {

	public RegisterationUserResponseRO saveUser(RegisterationUserRequestRO user);
	
	public boolean checkUsernameTaken(String username);

}

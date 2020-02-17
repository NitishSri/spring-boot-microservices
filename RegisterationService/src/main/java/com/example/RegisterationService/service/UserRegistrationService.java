package com.example.RegisterationService.service;

import com.example.RegisterationService.resourceobject.RegisterationUserRequestRO;
import com.example.RegisterationService.resourceobject.RegisterationUserResponseRO;
import com.example.RegisterationService.resourceobject.UserDetailsRO;

public interface UserRegistrationService {

	public RegisterationUserResponseRO saveUser(RegisterationUserRequestRO user);
	
	public boolean checkUsernameTaken(String username);
	
	public UserDetailsRO getUserDetails(String username);
	
	public UserDetailsRO updateUserDetails(UserDetailsRO userDetails);

}

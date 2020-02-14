package com.example.RegisterationService.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.RegisterationService.model.RegisterationUserInfo;
import com.example.RegisterationService.resourceobject.UserDetailsRO;

@Repository
public interface RegistrationRepository extends CrudRepository<RegisterationUserInfo, String> {

	Optional<RegisterationUserInfo> findByUsername(String Username);

}

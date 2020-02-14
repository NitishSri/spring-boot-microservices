package com.comparer.zuulapigateway.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.comparer.zuulapigateway.resource.LoginCredentials;

@Repository
public interface UserCredentialsRepository extends CrudRepository<LoginCredentials, String> {

	Optional<LoginCredentials> findByUsername(String username);

}

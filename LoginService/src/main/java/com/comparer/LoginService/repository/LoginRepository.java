package com.comparer.LoginService.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.comparer.LoginService.model.LoginCredentials;

@Repository
public interface LoginRepository extends CrudRepository<LoginCredentials, String> {

	Optional<LoginCredentials> findByUsername(String username);

}

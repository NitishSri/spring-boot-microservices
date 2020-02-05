package com.example.Docker;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface DockerRepo extends CrudRepository<DockerPOJO, String> {
	Optional<DockerPOJO> findByFirstname(String firstname);
}

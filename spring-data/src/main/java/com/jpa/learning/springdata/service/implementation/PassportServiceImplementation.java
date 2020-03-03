package com.jpa.learning.springdata.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.learning.springdata.repository.PassportRepository;
import com.jpa.learning.springdata.resourceObject.PassportRequest;
import com.jpa.learning.springdata.resourceObject.PassportResponse;
import com.jpa.learning.springdata.service.PassportService;

@Service
public class PassportServiceImplementation implements PassportService {

	@Autowired
	PassportRepository repository;

	@Override
	public List<PassportResponse> getAllPassportDetails() {
		return repository.getAllPassports();
	}

	@Override
	public PassportResponse addorUpdatePassport(PassportRequest request) {
		return repository.addorUpdatePassport(request);
	}

	@Override
	public String deletePassport(Long courseId) {
		return repository.deletePassport(courseId);
	}

}

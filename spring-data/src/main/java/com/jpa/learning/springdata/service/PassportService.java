package com.jpa.learning.springdata.service;

import java.util.List;

import com.jpa.learning.springdata.resourceObject.PassportRequest;
import com.jpa.learning.springdata.resourceObject.PassportResponse;

public interface PassportService {
	public List<PassportResponse> getAllPassportDetails();

	public PassportResponse addorUpdatePassport(PassportRequest request);

	public String deletePassport(Long courseId);

}
